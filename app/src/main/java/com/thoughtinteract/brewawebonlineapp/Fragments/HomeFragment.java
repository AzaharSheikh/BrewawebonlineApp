package com.thoughtinteract.brewawebonlineapp.Fragments;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.thoughtinteract.brewawebonlineapp.CustomAdapter.ProductCustomListAdapter;
import com.thoughtinteract.brewawebonlineapp.Database.DatabaseHandler;
import com.thoughtinteract.brewawebonlineapp.Model.Product;
import com.thoughtinteract.brewawebonlineapp.R;
import com.thoughtinteract.brewawebonlineapp.Utils.makeServiceCall;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements Animation.AnimationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String data;
    private List<Product> productList = new ArrayList<Product>();
    private ListView listView;
    private ProductCustomListAdapter adapter;
    private ProgressDialog pDialog;
    Animation zoomOut;
    ImageView img_dashboard;
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.content_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setDivider(null);
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, listView, false);
        ViewGroup footer = (ViewGroup)inflater.inflate(R.layout.list_view_footer, listView, false);
        listView.addHeaderView(header, null, false);
        listView.addFooterView(footer, null, false);
        zoomOut = AnimationUtils.loadAnimation(getActivity(),
                R.anim.zoom_out_anim);
        zoomOut.setAnimationListener(this);
        img_dashboard=(ImageView)rootView.findViewById(R.id.img_dashboard);
        img_dashboard.startAnimation(zoomOut);
        fetchListData();
        return rootView;
    }

    private void fetchListData() {
        new fetchListDataAsync().execute("http://172.17.11.18:80/brewawebonlinePHP/product_list.php");
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private class fetchListDataAsync extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                data = new makeServiceCall().makeServiceCall(url);
            } catch (Exception e) {
                e.printStackTrace();
                data="";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("data",data);
            pDialog.dismiss();
            if(data != null && !data.equalsIgnoreCase(""))
            {
                try {
                    JSONArray json = new JSONArray(data);
                    DatabaseHandler handler= new DatabaseHandler(getActivity());
                    SQLiteDatabase db = handler.getWritableDatabase();
                    for(int i =0; i<json.length();i++)
                    {
                        if(json.getJSONObject(i).has("product_id")) {
                            String product_id = json.getJSONObject(i).getString("product_id");
                            String product_title = json.getJSONObject(i).getString("product_title");
                            String product_details = json.getJSONObject(i).getString("product_details");
                            String p_address = json.getJSONObject(i).getString("p_address");
                            String image_url = json.getJSONObject(i).getString("image_url");
                            Log.d("product_id",product_id);
                            Product product = new Product();
                            product.setTitle(product_title);
                            product.setP_details(product_details);
                            product.setP_address(p_address);
                            product.setThumbnailUrl(image_url);
                            ContentValues values = new ContentValues();
                            values.put(DatabaseHandler.KEY_PRODUCT_ID,product_id);
                            values.put(DatabaseHandler.KEY_PRODUCT_TITLE,product_title);
                            values.put(DatabaseHandler.KEY_PRODUCT_DETAILS,product_details);
                            values.put(DatabaseHandler.KEY_PRODUCT_ADDRESS,p_address);
                            values.put(DatabaseHandler.KEY_IMAGE_URL,image_url);
                            boolean b = db.insert(DatabaseHandler.TABLE_PRODUCT,null,values)>0;
                            Log.d("insertproduct",b+"");
                            productList.add(product);
                        }

                    }
                    adapter = new ProductCustomListAdapter(getActivity(), productList);
                    listView.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
