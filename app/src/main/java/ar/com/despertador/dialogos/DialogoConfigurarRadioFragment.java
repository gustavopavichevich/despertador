package ar.com.despertador.dialogos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import ar.com.despertador.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogoConfigurarRadioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogoConfigurarRadioFragment extends DialogFragment {
    Activity actividad;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DialogoConfigurarRadioFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return CrearDialogoConfigurarRadio();
    }

    private AlertDialog CrearDialogoConfigurarRadio() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater= getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.fragment_dialogo_configurar_radio,null);

        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad=(Activity) context;
        }else{
            throw new RuntimeException(context.toString() + "");
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogoConfigurarRadioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogoConfigurarRadioFragment newInstance(String param1, String param2) {
        DialogoConfigurarRadioFragment fragment = new DialogoConfigurarRadioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogo_configurar_radio, container, false);
    }
    public void ElegirRadio()
    {
        /*Intent intent=new Intent(this, ListadoRadios.class);
        startActivity(intent);*/
    }
}