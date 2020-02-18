package com.phoneme.ticketing.ui.ticketing.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.phoneme.ticketing.R;

import com.phoneme.ticketing.config.RetrofitClientInstance;
import com.phoneme.ticketing.helper.VerticalSpaceItemDecoration;
import com.phoneme.ticketing.interfaces.GetDataService;
import com.phoneme.ticketing.ui.ticketing.FileDownloader;
import com.phoneme.ticketing.ui.ticketing.adapter.TicketListAdapterNew;
import com.phoneme.ticketing.ui.ticketing.model.TicketModel;
import com.phoneme.ticketing.ui.ticketing.model.TicketingViewModel;
import com.phoneme.ticketing.ui.ticketing.network.TicketResponse;
import com.phoneme.ticketing.ui.ticketing.sorting.ProjectNameCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketCreatedByCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketDescriptionCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketImageCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketImageTypeCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketNumberCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketStatusCompare;
import com.phoneme.ticketing.ui.ticketing.sorting.TicketTitleCompare;
import com.phoneme.ticketing.user.LoginActivity;
//import com.phoneme.ticketing.user.UserAuth;
import com.phoneme.ticketing.UserAuth;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketListFragment extends Fragment implements TicketListAdapterNew.OnItemClickListener {

    private TicketingViewModel galleryViewModel;
    private RecyclerView recyclerView;
    private List<TicketModel> ticketModelList = new ArrayList<>();
    private TextView ticketNumber, ticketTitle, project, ticketDescription, ticketStatus, ticketPriority, ticketCreatedBy,ticketImage,ticketImageType;
    //private TicketListAdapter adapter;
    private TicketListAdapterNew adapter;
    private Boolean ticketNumbersort = true, ticketTitlesort = true, projectNamesort = true, ticketDescriptionsort = true, ticketStatussort = true;
    private Boolean ticketPrioritysort, ticketCreatedBysort = true,ticketImagesort=true,ticketImageTypesort=true;
    private TextView ticketAdd,logOut;

    private String pdfurl4="https://www.phoneme.in/anujitbhu/ticketing/assets/images/ticket/Netgear_Data_sheet_NAS_3312-4312.pdf";
    private String pdfurl2="https://www.gutenberg.org/files/1342/old/pandp12p2.pdf";
    private String pdfurl="https://www.gutenberg.org/files/1342/old/pandp12p.pdf";
    private String pdfurl3="http://www.professorio.com/livebook/chapters/undying-spirit/professorio-undying-spirit-001.jpg";
    //private String foldername="phoneme/ticketing";
    //private String foldername="phoneme";
    private String foldername="";
    private ProgressDialog pDialog;

    public static final int progress_bar_type = 0;

    private RelativeLayout progressbarlayout;
    private int positionForDownload;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(TicketingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ticket_list, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_ticket_main);
        progressbarlayout=(RelativeLayout)view.findViewById(R.id.progressbar_relativelayout);

        VerticalSpaceItemDecoration itemDecoration = new VerticalSpaceItemDecoration(10);
        recyclerView.addItemDecoration(itemDecoration);
        ticketNumber = (TextView) view.findViewById(R.id.fragment_ticket_number);
        ticketTitle = (TextView) view.findViewById(R.id.fragment_ticket_title);
        project = (TextView) view.findViewById(R.id.fragment_project);
        ticketDescription = (TextView) view.findViewById(R.id.fragment_ticket_description);
        ticketStatus = (TextView) view.findViewById(R.id.fragment_ticket_status);
        ticketPriority = (TextView) view.findViewById(R.id.fragment_ticket_priority);
        ticketCreatedBy = (TextView) view.findViewById(R.id.fragment_ticket_created_by);
        ticketAdd = (TextView) view.findViewById(R.id.fragment_ticket_add);
        ticketImage=(TextView)view.findViewById(R.id.fragment_ticket_image);
        ticketImageType=(TextView)view.findViewById(R.id.fragment_ticket_image_type);
        logOut=(TextView)view.findViewById(R.id.fragment_logout);

        progressbarlayout.setVisibility(View.VISIBLE);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAuth userAuth1=new UserAuth(getContext());
                userAuth1.clearData();
                startLoginActivity();
            }
        });
        logOut.setVisibility(View.GONE);

        UserAuth userAuth = new UserAuth(getContext());

        if (userAuth.getRole().equals("2")) { //Only role 2 can create tickets
            ticketAdd.setVisibility(View.VISIBLE);
            //to be uncommented for post notification
            ticketAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //nav_ticket_add
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_ticket_add);
                }
            });
        } else {
            ticketAdd.setVisibility(View.GONE);
        }
        ticketNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketNumbersort) {
                    TicketNumberCompare ticketNumberCompare = new TicketNumberCompare();
                    Collections.sort(ticketModelList, ticketNumberCompare);
                    adapter.notifyDataSetChanged();
                    ticketNumbersort = false;
                } else {
                    TicketNumberCompare ticketNumberCompare = new TicketNumberCompare();
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketNumberCompare));
                    adapter.notifyDataSetChanged();
                    ticketNumbersort = true;
                }
            }
        });
        ticketTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketTitlesort) {
                    TicketTitleCompare ticketTitleCompare = new TicketTitleCompare();
                    Collections.sort(ticketModelList, ticketTitleCompare);
                    adapter.notifyDataSetChanged();
                    ticketTitlesort = false;
                } else {
                    TicketTitleCompare ticketTitleCompare = new TicketTitleCompare();
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketTitleCompare));
                    adapter.notifyDataSetChanged();
                    ticketTitlesort = true;
                }
            }
        });
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (projectNamesort) {
                    ProjectNameCompare projectNameCompare = new ProjectNameCompare();
                    Collections.sort(ticketModelList, projectNameCompare);
                    adapter.notifyDataSetChanged();
                    projectNamesort = false;
                } else {
                    ProjectNameCompare projectNameCompare = new ProjectNameCompare();
                    Collections.sort(ticketModelList, Collections.reverseOrder(projectNameCompare));
                    adapter.notifyDataSetChanged();
                    projectNamesort = true;
                }
            }
        });

        ticketDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketDescriptionsort) {
                    TicketDescriptionCompare ticketDescriptionCompare = new TicketDescriptionCompare();
                    Collections.sort(ticketModelList, ticketDescriptionCompare);
                    adapter.notifyDataSetChanged();
                    ticketDescriptionsort = false;
                } else {
                    TicketDescriptionCompare ticketDescriptionCompare = new TicketDescriptionCompare();
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketDescriptionCompare));
                    adapter.notifyDataSetChanged();
                    ticketDescriptionsort = true;
                }
            }
        });

        ticketStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ticketStatussort) {
                    TicketStatusCompare ticketStatusCompare = new TicketStatusCompare();
                    Collections.sort(ticketModelList, ticketStatusCompare);
                    adapter.notifyDataSetChanged();
                    ticketStatussort = false;
                } else {
                    TicketStatusCompare ticketStatusCompare = new TicketStatusCompare();
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketStatusCompare));
                    adapter.notifyDataSetChanged();
                    ticketStatussort = true;
                }
            }
        });

//        ticketPriority.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(ticketPrioritysort){
//                    TicketPriorityCompare ticketPriorityCompare=new TicketPriorityCompare();
//                    Collections.sort(ticketModelList,ticketPriorityCompare);
//                    adapter.notifyDataSetChanged();
//                    ticketPrioritysort=false;
//                }else{
//                    TicketPriorityCompare ticketPriorityCompare=new TicketPriorityCompare();
//                    Collections.sort(ticketModelList,Collections.reverseOrder(ticketPriorityCompare));
//                    adapter.notifyDataSetChanged();
//                    ticketPrioritysort=true;
//                }
//            }
//        });
        ticketCreatedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketCreatedByCompare ticketCreatedByCompare = new TicketCreatedByCompare();
                if (ticketCreatedBysort) {
                    Collections.sort(ticketModelList, ticketCreatedByCompare);
                    adapter.notifyDataSetChanged();
                    ticketCreatedBysort = false;
                } else {
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketCreatedByCompare));
                    adapter.notifyDataSetChanged();
                    ticketCreatedBysort = true;

                }
            }
        });

        ticketImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketImageCompare ticketImageCompare=new TicketImageCompare();
                if(ticketImagesort){
                    Collections.sort(ticketModelList,ticketImageCompare);
                    adapter.notifyDataSetChanged();
                    ticketImagesort=false;
                }else{
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketImageCompare));
                    adapter.notifyDataSetChanged();
                    ticketImagesort=true;
                }
            }
        });
        ticketImageType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketImageTypeCompare ticketImageTypeCompare=new TicketImageTypeCompare();
                if(ticketImageTypesort){
                    Collections.sort(ticketModelList,ticketImageTypeCompare);
                    adapter.notifyDataSetChanged();
                    ticketImageTypesort=false;
                }else{
                    Collections.sort(ticketModelList, Collections.reverseOrder(ticketImageTypeCompare));
                    adapter.notifyDataSetChanged();
                    ticketImageTypesort=true;
                }
            }
        });
        getTicketData();
    }
    private void startLoginActivity(){
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    private void setRecyclerView(List<TicketModel> ticketModelList) {

//        TicketListAdapter adapter=new TicketListAdapter(getContext(),ticketModelList,this);
        adapter = new TicketListAdapterNew(getContext(), ticketModelList, this);

        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearVertical = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearVertical);

    }

    private void getTicketData() {
        //GetDataService service= RetrofitClientInstance.getRetrofitInstance(getContext()).create(GetDataService.class);//getContext() means apply jwttoken in header
        GetDataService service = RetrofitClientInstance.APISetup(getActivity()).create(GetDataService.class);

        Call<TicketResponse> call = service.getTickets();
        UserAuth userAuth = new UserAuth(getContext());
        //Toast.makeText(getContext(), "getTicketData=" + userAuth.getJwtToken(), Toast.LENGTH_LONG).show();
        call.enqueue(new Callback<TicketResponse>() {
            @Override
            public void onResponse(Call<TicketResponse> call, Response<TicketResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("Response successfull ra\n" + response.body().getListOfTickets());
                    ticketModelList = response.body().getListOfTickets();
//                    setRecyclerView(response.body().getListOfTickets());
                    progressbarlayout.setVisibility(View.GONE);
                    setRecyclerView(ticketModelList);

                }else{
                    progressbarlayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<TicketResponse> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure" + t.getMessage(), Toast.LENGTH_LONG).show();
                //Toast.makeText(getContext(),"onFailure"+t.getCause().getMessage(),Toast.LENGTH_LONG).show();
                System.out.println("ticketlistfragment onFailure " + t.getMessage());
                progressbarlayout.setVisibility(View.GONE);
            }
        });
    }

    public void onItemClick(int position) {
        //to be uncommented for post notification
        //Bundle args2 = TicketListFragmentDirections.navTicketingAction().getArguments();
        Bundle args2=new Bundle();
        args2.putString("ticket_id", ticketModelList.get(position).getId());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_ticketing_action, args2);
    }

    public void onTicketNumberClick(int position) {
        //to be uncommented for post notificaiotn
        //Bundle args2 = TicketListFragmentDirections.navTicketingAction().getArguments();
        Bundle args2=new Bundle();
        args2.putString("ticket_id", ticketModelList.get(position).getId());
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_ticketing_action_view, args2);
    }
    public void onDownloadLinkClick(int position){
        //Toast.makeText(getContext(), "onDonwloadLinkClick" , Toast.LENGTH_LONG).show();
        //new DownloadFileFromURL().execute(pdfurl,pdfurl,pdfurl);
        //download(pdfurl3);
 //       new DownloadFileFromURL2().execute(pdfurl);
        positionForDownload=position;
        haveStoragePermission(position);
//        download(ticketModelList.get(position).getImage());
        //if(haveStoragePermission(position)){
//            download(ticketModelList.get(position).getImage());
        //}
//        download(ticketModelList.get(position).getImage());
    }


//    class DownloadFileFromURL extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//
//            try {
//                String root = Environment.getExternalStorageDirectory().toString();
//                Toast.makeText(getContext(), "DownloadFileFromURL1" , Toast.LENGTH_LONG).show();
//                System.out.println("Downloading");
//                URL url = new URL(f_url[0]);
//                String filename=f_url[0].substring(f_url[0].lastIndexOf('/'), f_url[0].length());//Create file name by picking download file name from URL
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                // getting file length
//                int lenghtOfFile = conection.getContentLength();
//
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                // Output stream to write file
//
//                OutputStream output = new FileOutputStream(root+"/"+filename);
//
//                byte data[] = new byte[1024];
//
//                long total = 0;
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//
//                    // writing data to file
//                    output.write(data, 0, count);
//
//                }
//
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//                Toast.makeText(getContext(), "DownloadFileFromURLfinal" , Toast.LENGTH_LONG).show();
//            }
//            catch (Exception e){
//                Toast.makeText(getContext(), "DownloadFileFromURLfinal Exception"+e.getMessage() , Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//    }


//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case progress_bar_type: // we set this to 0
//                pDialog = new ProgressDialog(getContext());
//                pDialog.setMessage("Downloading file. Please wait...");
//                pDialog.setIndeterminate(false);
//                pDialog.setMax(100);
//                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                pDialog.setCancelable(true);
//                pDialog.show();
//                return pDialog;
//            default:
//                return null;
//        }
//    }
    public void download(String url)
    {
        //new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
        //new DownloadFile().execute("http://www.professorio.com/inspirationalposters/inspiposters/do-what-makes-you-happy.jpg","maven.jpg");
        String filename=url.substring(url.lastIndexOf('/'), url.length());//Create file name by picking download file name from URL
        Toast.makeText(getActivity(), "Download of '"+filename.substring(1)+"' started" , Toast.LENGTH_LONG).show();
        new DownloadFile().execute(url, filename);
    }
    private class DownloadFile extends AsyncTask<String, Void, Void> {
        private String filenamera;
        @Override
        protected Void doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            this.filenamera=strings[1];
            //String extStorageDirectory = Environment.getExternalStorageDirectory().toString();//saves in sdcard . not sure
            String extStorageDirectory  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();//saves in default download directory
            File folder = new File(extStorageDirectory, foldername);
            folder.mkdir();

            File pdfFile = new File(folder, fileName);
            //System.out.println("doinbackgroundra");
            try{
                pdfFile.createNewFile();
                //System.out.println("doinbackgroundra creating file");
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("doinbackgroundra file create exception");
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            System.out.println("doinbackgroundra complete");
            Toast.makeText(getActivity(), "Download of '"+filenamera.substring(1)+"' is complete" , Toast.LENGTH_LONG).show();
            //view(this.filenamera);

        }
//        @Override
//        protected void onPreExecute()
//        {
//            //super.onPreExecute();
//           //getActivity().finish();
//        }
    }
//commented on 9th jan. Will be deleted if everything works well
//    class DownloadFileFromURL2 extends AsyncTask<String, String, String> {
//
//        /**
//         * Before starting background thread
//         * Show Progress Bar Dialog
//         * */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            //showDialog(progress_bar_type);
//        }
//
//        /**
//         * Downloading file in background thread
//         * */
//        @Override
//        protected String doInBackground(String... f_url) {
//            int count;
//            //Toast.makeText(getContext(), "doInbackgroundDownloadFileFromURL2" , Toast.LENGTH_LONG).show();
//            try {
//                URL url = new URL(f_url[0]);
//                URLConnection conection = url.openConnection();
//                conection.connect();
//                Toast.makeText(getContext(), "DownloadFileFromURL2" , Toast.LENGTH_LONG).show();
//                // this will be useful so that you can show a tipical 0-100% progress bar
//                int lenghtOfFile = conection.getContentLength();
//
//                // download the file
//                InputStream input = new BufferedInputStream(url.openStream(), 8192);
//
//                // Output stream
//                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
//
//                byte data[] = new byte[1024];
//
//                long total = 0;
//
//                while ((count = input.read(data)) != -1) {
//                    total += count;
//                    // publishing the progress....
//                    // After this onProgressUpdate will be called
//                    publishProgress(""+(int)((total*100)/lenghtOfFile));
//
//                    // writing data to file
//                    output.write(data, 0, count);
//                }
//
//                // flushing output
//                output.flush();
//
//                // closing streams
//                output.close();
//                input.close();
//
//            } catch (Exception e) {
//                Log.e("Errorra: ", e.getMessage());
////                Toast.makeText(getContext(), "error DownloadFileFromURL1" , Toast.LENGTH_LONG).show();
//            }
//
//            return null;
//        }
//
//        /**
//         * Updating progress bar
//         * */
//        protected void onProgressUpdate(String... progress) {
//            // setting progress percentage
//            pDialog.setProgress(Integer.parseInt(progress[0]));
//        }
//
//        /**
//         * After completing background task
//         * Dismiss the progress dialog
//         * **/
//        @Override
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog after the file was downloaded
//            //dismissDialog(progress_bar_type);
//
//            // Displaying downloaded image into image view
//            // Reading image path from sdcard
//            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
//            // setting downloaded into image view
//            //my_image.setImageDrawable(Drawable.createFromPath(imagePath));
//        }
//
//    }

    public void view(String filename)
    {
        System.out.println("doinbackground view just inside");
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/"+foldername+"/" + filename);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW,path);
        //pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        System.out.println("doinbackground view");
        try{
            System.out.println("doinbackground view startactivity");
            getActivity().startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            System.out.println("doinbackground ActivityNotFoundException");
        }
    }


    public  boolean haveStoragePermission(int position) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                download(ticketModelList.get(position).getImage());
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            download(ticketModelList.get(position).getImage());
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            download(ticketModelList.get(positionForDownload).getImage());
        }
    }
}