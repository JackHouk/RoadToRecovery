package jack.roadtorecovery;

import android.app.Activity;
import android.content.Context;
import android.app.AlertDialog;
import android.widget.TextView;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Network.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Network#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Network extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //stored reference to activity's layout inflater
    private static LayoutInflater mLi;
    //Master list of contact entries
    private static List contacts = new ArrayList<ContactEntry>();
    //Contains and adapts the data array to be used in the contacts view
    private static GroupedListAdapter contactsArray;
    //ListView to display from ListAdapter
    private static ListView contactsView;

    private static TextView resultText;

    private Button addPerson;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Network() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Network.
     */
    // TODO: Rename and change types and number of parameters
    public static Network newInstance(String param1, String param2) {
        Network fragment = new Network();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static void onAddPerson(Activity activ){
        String name, phone, group;
        LayoutInflater layoutInflater = LayoutInflater.from(activ);
        View promptView = layoutInflater.inflate(R.layout.create_contact_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activ);
        alertDialogBuilder.setView(promptView);

        final EditText editName = (EditText) promptView.findViewById(R.id.editname);
        final EditText editPhone = (EditText) promptView.findViewById(R.id.editphone);
        final EditText editGroup = (EditText) promptView.findViewById(R.id.editgroup);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Network.contacts.add(new ContactEntry(editName.getText().toString(),
                                                        editPhone.getText().toString(),
                                                        editGroup.getText().toString()));
                        ContactEntry toAdd = (ContactEntry)contacts.get(contacts.size() - 1);
                        contactsArray.addItem(toAdd.mName, toAdd.mGroup);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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
        //save reference to layout inflater
        mLi = inflater;
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mListener.attachAddPerson(addPerson);
        mListener.buildContactView(contactsView, contactsArray);
        return;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        contactsArray = new GroupedListAdapter(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
        void buildContactView(ListView contactsView, GroupedListAdapter contactsArray);
        void attachAddPerson(Button addPerson);
        //void onAddPerson();
    }
}