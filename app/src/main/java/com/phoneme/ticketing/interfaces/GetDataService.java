package com.phoneme.ticketing.interfaces;

import com.phoneme.ticketing.BuildConfig;
import com.phoneme.ticketing.ui.company.model.CompanyModel;
import com.phoneme.ticketing.ui.company.network.CompanyCreatePostResponse;
import com.phoneme.ticketing.ui.company.network.CompanyEditGetResponse;
import com.phoneme.ticketing.ui.company.network.CompanyEditPostResponse;
import com.phoneme.ticketing.ui.dashboard.DashboardApi;
import com.phoneme.ticketing.ui.dashboard.network.DashboardMainResponse;
import com.phoneme.ticketing.ui.inframonitoring.InfraMonitoringResponse;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponse2;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponseCheck;
import com.phoneme.ticketing.ui.project.network.ProjectAddGetResponse;
import com.phoneme.ticketing.ui.project.network.ProjectAddPostResponse;
import com.phoneme.ticketing.ui.project.network.ProjectEditGetResponse;
import com.phoneme.ticketing.ui.project.network.ProjectEditPostResponse;
import com.phoneme.ticketing.ui.project.network.ProjectListResponse;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponse;
import com.phoneme.ticketing.ui.productivity.network.TechsupportDashboardResponseList;
import com.phoneme.ticketing.ui.ticketing.network.TicketCreatGetResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketCreatePostResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketEditPostResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketEditResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketGetViewResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketPostViewResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketResponse;
import com.phoneme.ticketing.ui.ticketing.network.TicketSingleResponse;
import com.phoneme.ticketing.ui.user.network.UserAddPostResponse;
import com.phoneme.ticketing.ui.user.network.UserEditGetResponse;
import com.phoneme.ticketing.ui.user.network.UserEditResponse;
import com.phoneme.ticketing.ui.user.network.UserListResponse;
import com.phoneme.ticketing.ui.user.network.UserPasswordChangeResponse;
import com.phoneme.ticketing.user.network.FCMTokenUploadResponse;
import com.phoneme.ticketing.user.network.OTPVerifactionResponse;
import com.phoneme.ticketing.user.network.PhonePostResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface GetDataService {
    // https://phoneme.in/android/napolean
    @GET(BuildConfig.MIDDLE_URL +"dashboard/index1")
    Call<DashboardApi> getDashboardData();

    @GET(BuildConfig.MIDDLE_URL +"dashboard/index")
    Call<DashboardMainResponse> getDashboardMainData();

//    @GET("/android/napolean/ticketingapici/ticket/ticket") //without authorization. It is working
//    Call<TicketResponse> getTickets();

//    @GET("/android/napolean/ticketingapici/ticket/index")
//    Call<TicketResponse> getTickets();

    @GET(BuildConfig.MIDDLE_URL +"ticket/index")
    Call<TicketResponse> getTickets();

    @GET(BuildConfig.MIDDLE_URL +"ticket/index1")
    Call<TicketResponse> getMyTickets();

    @GET(BuildConfig.MIDDLE_URL +"company/")
    Call<List<CompanyModel>> getCompanies();

    @GET(BuildConfig.MIDDLE_URL +"company/edit")
    Call<CompanyEditGetResponse> getCompanyData(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"ticket/view1")
    Call<TicketSingleResponse> getTicketView1(@Query("id") String id);

//    @GET(BuildConfig.MIDDLE_URL +"productivity/")//This one original. Nnot to be used for a while
//    Call<TechsupportDashboardResponse> getProductivity();//because of biru changes

    @GET(BuildConfig.MIDDLE_URL +"productivity/index2")//This one original not to be used for a while
    Call<TechsupportDashboardResponse> getProductivity();//because of biru changes

    @GET(BuildConfig.MIDDLE_URL +"productivity/index4")//This one original not to be used for a while
    Call<TechsupportDashboardResponseCheck> getProductivityCheck();//because of biru changes

    @GET(BuildConfig.MIDDLE_URL +"productivity/")
    Call<TechsupportDashboardResponse> getUserProductivity(@Query("uid") String id);

    @GET(BuildConfig.MIDDLE_URL +"productivity/index2")//Befor biru changes it was /index or just /
    Call<TechsupportDashboardResponseList> getUserProductivityList(@Query("uid") String id);

    @GET(BuildConfig.MIDDLE_URL +"productivity/index3")//Befor biru changes it was /index or just /
    Call<TechsupportDashboardResponseList> getUserProductivityListWithStatus(@Query("uid") String id,@Query("solved") String status);

    @GET(BuildConfig.MIDDLE_URL +"user/")
    Call<UserListResponse> getUserList();

    @GET(BuildConfig.MIDDLE_URL +"user/getGCMAppString")
    Call<String> getGCMMasterDataForaUser(@Query("gcm_master_id") String id);

    @GET(BuildConfig.MIDDLE_URL +"user/edit")
    Call<UserEditGetResponse> getUserData(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"ticket/edit")
    Call<TicketEditResponse> getTicketEdit(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"ticket/view")
    Call<TicketGetViewResponse> getTicketView(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"ticket/create")
    Call<TicketCreatGetResponse> getTicketCreate();

    @GET(BuildConfig.MIDDLE_URL +"project/")
    Call<ProjectListResponse> getProjectList();

    @GET(BuildConfig.MIDDLE_URL +"project/edit")
    Call<ProjectEditGetResponse> getProjectEdit(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"project/listoftickets")
    Call<TicketResponse> getTicketsForaGivenProject(@Query("id") String id);

    @GET(BuildConfig.MIDDLE_URL +"project/add")
    Call<ProjectAddGetResponse> getDataForProjectAdd();

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"ticket/view")
    Call<TicketPostViewResponse> postTicketView(@FieldMap Map<String, String> postTicketData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"project/add")
    Call<ProjectAddPostResponse> postProjectAdd(@FieldMap Map<String, String> projectData, @Field("allocated_users[]") List<String> items);

    //Added feb 20 2020 post biru changes

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"project/add")
    Call<ProjectAddPostResponse> postProjectAddWithOutImage(@PartMap() Map<String, RequestBody> partMap,@Query("allocated_users[]") List<String> user_id);

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"project/add")
    Call<ProjectAddPostResponse> postProjectAddWithImage(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap,@Query("allocated_users[]") List<String> user_id);

//    @FormUrlEncoded
//    @POST(BuildConfig.MIDDLE_URL +"project/editpost")
//    Call<ProjectEditPostResponse> postCompanyEdit(@FieldMap Map<String, String> proejectData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"project/editpost")
    Call<ProjectEditPostResponse> postCompanyEdit2(@FieldMap Map<String, String> projectData, @FieldMap Map<String, List<String>> map2);

//    @FormUrlEncoded //commented on dec26 2019
//    @POST(BuildConfig.MIDDLE_URL +"project/editpost")
//    Call<ProjectEditPostResponse> postCompanyEdit(@FieldMap Map<String,String> projectData,@Field("allocated_users[]") List<String> items);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"project/edit")
    Call<ProjectEditPostResponse> postCompanyEdit(@FieldMap Map<String, String> projectData, @Field("allocated_users[]") List<String> items);


    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"company/create")
    Call<CompanyCreatePostResponse> postCompanyCreate(@Field("Company_name") String name, @Field("status") int status);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"company/edit")
    Call<CompanyEditPostResponse> postCompanyEditFinal(@Field("id") String id, @Field("Company_name") String name, @Field("status") int status);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
    Call<TicketCreatePostResponse> postTicketAdd(@FieldMap Map<String, String> ticketData);

    //Biru user allocated
    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
    Call<TicketCreatePostResponse> postTicketAddWithUser(@FieldMap Map<String, String> ticketData,@Field("check_user[]") List<String> chkusrs);

//    @FormUrlEncoded
//    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
//    Call<TicketCreatePostResponse> postTicketAddWithImage(@FieldMap Map<String, String> ticketData);

//    @Multipart
//    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
//    Call<TicketCreatePostResponse> postTicketAddWithImage(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap,@Query("check_user[]") ArrayList<String> user_id);

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
    Call<TicketCreatePostResponse> postTicketAddWithOutImage(@PartMap() Map<String, RequestBody> partMap,@Part("check_user[]") ArrayList<Integer> user_id);

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"ticket/create")
    Call<TicketCreatePostResponse> postTicketAddWithImage(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap,@Part("check_user[]") ArrayList<Integer> user_id);



    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/otpverification")
    Call<OTPVerifactionResponse> postOTP(@FieldMap Map<String, String> otpData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/passwordchange")
    Call<UserPasswordChangeResponse> postPasswordChange(@FieldMap Map<String, String> pwdData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"login/index")
    Call<OTPVerifactionResponse> postLoginPassword(@FieldMap Map<String, String> otpData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/postphoneforlogin")
    Call<PhonePostResponse> postPhone(@FieldMap Map<String, String> phone);


    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/financeGCM")
    Call<FCMTokenUploadResponse> postFCMToken(@FieldMap Map<String, String> userData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/financeGCM")
    Call<String> postCompletUserData(@FieldMap Map<String, String> userData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/postUpdateUserDataTable")
    Call<String> postUpdateUserDataTable(@FieldMap Map<String, String> userData);

    @FormUrlEncoded
    @POST(BuildConfig.MIDDLE_URL +"user/updateGCMMasterTable")
    Call<String> postUpdateGCMTable(@FieldMap Map<String, String> userData);

    //commented on dec 26 2019
//    @POST(BuildConfig.MIDDLE_URL +"ticket/editpost")
//    @FormUrlEncoded
//    Call<TicketEditPostResponse> postTicketEdit(@Field("ticket_title") String name,
//                                                @Field("ticket_desc") String description,
//                                                @Field("project_id") String projectid,
//                                                @Field("id") String id,
//                                                @Field("priority") String priority,
//                                                @Field("last_updated_by") int lastupdatedby);

    //Added on dec26 2019
    @POST(BuildConfig.MIDDLE_URL +"ticket/edit")
    @FormUrlEncoded
    Call<TicketEditPostResponse> postTicketEdit(@Field("ticket_title") String name,
                                                @Field("ticket_desc") String description,
                                                @Field("project_id") String projectid,
                                                @Field("id") String id,
                                                @Field("priority") String priority,
                                                @Field("last_updated_by") int lastupdatedby);

    //Added on dec26 2019
    @POST(BuildConfig.MIDDLE_URL +"ticket/edit")
    @FormUrlEncoded
    Call<TicketEditPostResponse> postTicketEditMap(@FieldMap Map<String, String> ticketEditData);

    //Added on feb 14 2020
    @POST(BuildConfig.MIDDLE_URL +"ticket/edit")
    @FormUrlEncoded
    Call<TicketEditPostResponse> postTicketEditMapUser(@FieldMap Map<String, String> ticketEditData,@Field("check_user[]") List<String> chkusrs);


    @POST(BuildConfig.MIDDLE_URL +"user/posteditwithoutimage")
    @FormUrlEncoded
    Call<UserEditResponse> postUserdata2(@Field("name") String name,
                                         @Field("mobilenum") String mobilenum,
                                         @Field("status") String status_new,
                                         @Field("id") String id,
                                         @Field("email") String email);


    //This '/android/napolean"+ BuildConfig.MIDDLE_URL +"user/profile' to be used instead of     @POST(BuildConfig.MIDDLE_URL +"user/posteditwithoutimage")
//    @Multipart
//    @POST(BuildConfig.MIDDLE_URL +"user/profile")
//    @FormUrlEncoded
//    Call<UserEditResponse> postUserdataWithImage(@Part MultipartBody.Part file,@Part Map<String, RequestBody> userData);

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"user/profile")
    Call<UserEditResponse> postUserdataWithImage(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);


    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"user/profile")
    Call<UserEditResponse> postUserdataWithoutImage(@PartMap() Map<String, RequestBody> userData);


    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"user/add")
    Call<UserAddPostResponse> postUserAddWithImage(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> partMap);

//    @Multipart
//    @POST(BuildConfig.MIDDLE_URL +"user/profile")
//    Call< UserAddPostResponse> postUserAddWithoutImage(@PartMap() Map<String,RequestBody> userData);

    @Multipart
    @POST(BuildConfig.MIDDLE_URL +"user/add")
    Call<UserAddPostResponse> postUserAddWithoutImage(@PartMap() Map<String, RequestBody> userData);


    

    @GET("/anujitbhu/ticketing/API/serverlogsmobile")//Need to change url  //url changed
    Call<InfraMonitoringResponse> getInframonitoringData();

    @POST("/anujitbhu/ticketing/API/serverlogsmobile") //Need to change url //url changed
    @FormUrlEncoded
    Call<InfraMonitoringResponse> postInframonitoringData(@FieldMap Map<String, String> ticketEditData);

    @POST("/logs/executemobile.php") //Need to change url //url changed
    @FormUrlEncoded
    Call<String> postInframonitoringDataCommand(@FieldMap Map<String, String> ticketEditData);

}
