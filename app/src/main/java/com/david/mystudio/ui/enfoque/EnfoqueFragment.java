package com.david.mystudio.ui.enfoque;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.david.mystudio.R;
import com.david.mystudio.StudyApplication;
import com.david.mystudio.domain.model.Materia;
import com.david.mystudio.domain.model.TipoTecnica;
import com.google.android.material.button.MaterialButton;
import java.util.List;

/**
 * Fragmento de la Pantalla 3: Enfoque Zanshin (Temporizador).
 */
public class EnfoqueFragment extends Fragment implements EnfoqueContract.View, StudyTimerService.OnTimerTickListener {

    private EnfoqueContract.Presenter presenter;
    private Spinner spnAsignatura;
    private TextView txtTiempo;
    private TextView txtEstado;
    private MaterialButton btnPausarReanudar;
    private MaterialButton btnFinalizar;
    private MaterialButton btnIniciar;
    private EditText edtNotas;

    private StudyTimerService timerService;
    private boolean servicioEnlazado = false;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            StudyTimerService.LocalBinder localBinder = (StudyTimerService.LocalBinder) binder;
            timerService = localBinder.getService();
            timerService.setListener(EnfoqueFragment.this);
            servicioEnlazado = true;
            if (presenter != null) {
                presenter.restaurarSesionActivaSiExiste();
                if (timerService.isEstaCorriendo()) {
                    presenter.sincronizarConServicio(timerService.getSegundosTranscurridos(), timerService.isEstaPausado());
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
            servicioEnlazado = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enfoque, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StudyApplication app = (StudyApplication) requireActivity().getApplication();
        presenter = new EnfoquePresenter(
                app.getAppContainer().getMateriaService(),
                app.getAppContainer().getSesionEstudioService()
        );
        presenter.vincularVista(this);

        spnAsignatura = view.findViewById(R.id.spnAsignaturaEnfoque);
        txtTiempo = view.findViewById(R.id.txtTiempoCronometro);
        txtEstado = view.findViewById(R.id.txtEstadoCronometro);
        btnPausarReanudar = view.findViewById(R.id.btnPausarReanudarEnfoque);
        btnFinalizar = view.findViewById(R.id.btnFinalizarEnfoque);
        btnIniciar = view.findViewById(R.id.btnIniciarSesionEnfoque);
        edtNotas = view.findViewById(R.id.edtNotasSesion);

        btnIniciar.setOnClickListener(v -> {
            Materia seleccionada = (Materia) spnAsignatura.getSelectedItem();
            if (seleccionada == null) {
                Toast.makeText(getContext(), R.string.enfoque_sin_materia_alerta, Toast.LENGTH_SHORT).show();
                return;
            }
            presenter.iniciarSesion(seleccionada.getId(), TipoTecnica.POMODORO);
        });

        btnPausarReanudar.setOnClickListener(v -> {
            if (timerService != null) {
                if (timerService.isEstaPausado()) {
                    timerService.reanudar();
                    presenter.alternarPausa(false);
                } else {
                    timerService.pausar();
                    presenter.alternarPausa(true);
                }
            }
        });

        btnFinalizar.setOnClickListener(v -> {
            String notas = edtNotas.getText() != null ? edtNotas.getText().toString().trim() : "";
            if (timerService != null) {
                timerService.detener();
            }
            presenter.finalizarSesion(notas);
        });

        enlazarServicio();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.cargarMaterias();
            presenter.restaurarSesionActivaSiExiste();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timerService != null) {
            timerService.setListener(null);
        }
        desenlazarServicio();
        if (presenter != null) {
            presenter.desvincularVista();
        }
    }

    private void enlazarServicio() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent(context, StudyTimerService.class);
            context.startService(intent);
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void desenlazarServicio() {
        if (servicioEnlazado && getContext() != null) {
            getContext().unbindService(serviceConnection);
            servicioEnlazado = false;
        }
    }

    @Override
    public void mostrarMaterias(List<Materia> materias) {
        if (getContext() == null) return;
        if (materias == null || materias.isEmpty()) {
            Toast.makeText(getContext(), "No tienes materias registradas para estudiar", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayAdapter<Materia> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, materias);
        spnAsignatura.setAdapter(adapter);
    }

    @Override
    public void seleccionarMateriaActiva(int idMateria) {
        if (spnAsignatura == null || spnAsignatura.getAdapter() == null) return;
        for (int i = 0; i < spnAsignatura.getAdapter().getCount(); i++) {
            Object item = spnAsignatura.getAdapter().getItem(i);
            if (item instanceof Materia && ((Materia) item).getId() == idMateria) {
                spnAsignatura.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void actualizarTiempo(String tiempoFormateado) {
        if (txtTiempo != null) {
            txtTiempo.setText(tiempoFormateado);
        }
    }

    @Override
    public void actualizarEstadoSesion(boolean corriendo, boolean pausado) {
        if (!corriendo) {
            btnIniciar.setVisibility(View.VISIBLE);
            btnPausarReanudar.setVisibility(View.GONE);
            btnFinalizar.setVisibility(View.GONE);
            txtEstado.setText("Estado: En espera de inicio");
        } else {
            btnIniciar.setVisibility(View.GONE);
            btnPausarReanudar.setVisibility(View.VISIBLE);
            btnFinalizar.setVisibility(View.VISIBLE);
            if (pausado) {
                txtEstado.setText(R.string.enfoque_estado_pausado);
                btnPausarReanudar.setText(R.string.enfoque_btn_reanudar);
                btnPausarReanudar.setIconResource(R.drawable.ic_play_arrow);
            } else {
                txtEstado.setText(R.string.enfoque_estado_activo);
                btnPausarReanudar.setText(R.string.enfoque_btn_pausar);
                btnPausarReanudar.setIconResource(R.drawable.ic_pause);
            }
        }
    }

    @Override
    public void mostrarResumenSesionCompletada(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void limpiarNotas() {
        if (edtNotas != null) {
            edtNotas.setText("");
        }
    }

    @Override
    public void mostrarCargando(boolean cargando) {}

    @Override
    public void mostrarError(String mensaje) {
        if (getContext() != null) {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void iniciarTemporizadorVisual(int segundosIniciales) {
        if (timerService != null) {
            timerService.iniciar(segundosIniciales);
        }
    }

    @Override
    public void onTick(int segundosTranscurridos, boolean estaPausado) {
        if (presenter != null) {
            presenter.procesarTickSegundos(segundosTranscurridos, estaPausado);
        }
    }
}
