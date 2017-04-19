package whitaker.anthony.herome.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import whitaker.anthony.herome.R;
import whitaker.anthony.herome.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.MainFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String param1;
    private String param2;

    @Deprecated private Button accidentButton;
    @Deprecated private Button mutationButton;
    @Deprecated private Button birthButton;
    private Button choosePowersButton;

    private MainFragmentInteractionListener listener;

    enum PowerOrigin {
        ACCIDENT(R.id.accidentButton, R.drawable.lightning_icon),
        MUTATION(R.id.mutationButton, R.drawable.atomic_icon),
        BIRTH(R.id.birthButton, R.drawable.rocket_icon);

        public final int buttonId;
        public final int iconId;

        PowerOrigin(int buttonId, int iconId) {
            this.buttonId = buttonId;
            this.iconId = iconId;
        }

        private static PowerOrigin fromButtonId(int buttonId) {
            for(PowerOrigin powerOrigin : values()) {
                if(powerOrigin.buttonId == buttonId) {
                    return  powerOrigin;
                }
            }

            return null;
        }
    }

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
            param1 = getArguments().getString(ARG_PARAM1);
            param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        for(PowerOrigin powerOrigin : PowerOrigin.values()) {
            Button button = (Button)view.findViewById(powerOrigin.buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPowerOriginButtonPressed((Button) v);
                }
            });
        }

        accidentButton = (Button)view.findViewById(R.id.accidentButton);
        mutationButton = (Button)view.findViewById(R.id.mutationButton);
        birthButton = (Button)view.findViewById(R.id.birthButton);
        choosePowersButton = (Button)view.findViewById(R.id.choosePowersButton);
        choosePowersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.loadPickPowerScreen();
            }
        });

        choosePowersButton.setEnabled(false);
        choosePowersButton.getBackground().setAlpha(128);

        // Inflate the layout for this fragment
        return view;
    }

    public void onPowerOriginButtonPressed(Button button) {
        choosePowersButton.setEnabled(true);
        choosePowersButton.getBackground().setAlpha(255);

        PowerOrigin powerOrigin = PowerOrigin.fromButtonId(button.getId());
        if(powerOrigin == null) return;

        resetPowerOriginButtons();
        button.setCompoundDrawablesWithIntrinsicBounds(powerOrigin.iconId,0,R.drawable.item_selected_btn,0);
    }

    private void resetPowerOriginButtons() {
        if(getView() == null) return;
        for(PowerOrigin powerOrigin : PowerOrigin.values()) {
            Button button = (Button)getView().findViewById(powerOrigin.buttonId);
            button.setCompoundDrawablesWithIntrinsicBounds(powerOrigin.iconId,0,0,0);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (listener != null) {
            listener.onMainFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainFragmentInteractionListener) {
            listener = (MainFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MainFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMainFragmentInteraction(Uri uri);
    }
}
