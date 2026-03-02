package com.hondacrm.Retrofit;

import com.hondacrm.Module.BranchResponse;
import com.hondacrm.Module.CategoryResponse;
import com.hondacrm.Module.ContactResponse;
import com.hondacrm.Module.CountResponse;
import com.hondacrm.Module.EnquiryMaxNumberResponse;
import com.hondacrm.Module.EnquiryResponse;
import com.hondacrm.Module.FeedbackResponse;
import com.hondacrm.Module.FinanceResponse;
import com.hondacrm.Module.FollowupResponse;
import com.hondacrm.Module.LoginResponse;
import com.hondacrm.Module.ModelColorResponse;
import com.hondacrm.Module.ModelNameResponse;
import com.hondacrm.Module.ModelPriceResponse;
import com.hondacrm.Module.ModelVariantResponse;
import com.hondacrm.Module.QuotationResponse;
import com.hondacrm.Module.SourceResponse;
import com.hondacrm.Module.StageResponse;
import com.hondacrm.Module.TodayFollowupResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/api/user/{id}")
    Call<List<LoginResponse>> checkMobileNumber(@Path("id") String mobileNumber);

    @GET("/Api/ContactAll")
    Call<List<ContactResponse>> getAdminContactList();

    @GET("/Api/ContactAll")
    Call<List<ContactResponse>> getContactList();

    @GET("/Api/Contact")
    Call<List<ContactResponse>> getAdminContactPaginationList(@Query("pageNumber") int pageNumber,
                                                              @Query("pageSize") int pageSize);

    @GET("/Api/Contact")
    Call<List<ContactResponse>> getContactPaginationList(@Query("id") String id,
                                                         @Query("pageNumber") int pageNumber,
                                                         @Query("pageSize") int pageSize);

    @GET("/Api/ModelCategory")
    Call<List<CategoryResponse>> getCategoryList();

    @GET("/Api/ModelName")
    Call<List<ModelNameResponse>> getModelNameList(@Query("id") String categoryId);

    @GET("/Api/ModelVarient")
    Call<List<ModelVariantResponse>> getModelvariantList(@Query("id") String modelNameId);

    @GET("/Api/Products")
    Call<List<ModelVariantResponse>> getProductsList(@Query("id") String modelNameId);

    @GET("/Api/ModelColor")
    Call<List<ModelColorResponse>> getModelColorList(@Query("id") String modelNameId);

    @GET("/Api/Enqtype")
    Call<List<EnquiryResponse>> getEnquiryTypeList();

    @GET("/Api/Source")
    Call<List<SourceResponse>> getSourceList();

    @GET("Api/Stage")
    Call<List<StageResponse>> getStageList();

    @GET("Api/Branch")
    Call<List<BranchResponse>> getBranchList(@Query("id") String branchId);

    @GET("Api/ubranch")
    Call<List<BranchResponse>> getAdminBranchList();

    @GET("api/ubranch")
    Call<List<BranchResponse>> getUserBranchList(@Query("id") String branchId);

    @POST("Api/Insert/Enquiry")
    Call<String> addEnquiry(@Query("Date") String date,
                            @Query("ContactId") String contactId,
                            @Query("ModelCat") String categoryId,
                            @Query("ModelName") String modelNameId,
                            @Query("Product") String productId,
                            @Query("ModelColor") String colorId,
                            @Query("PurchaseType") String purchaseNameId,
                            @Query("EnqType") String enquiryId,
                            @Query("CustomerType") String customerTypeId,
                            @Query("Branch") String branchId,
                            @Query("Source") String sourceId,
                            @Query("Status") String statusId,
                            @Query("Stage") String stageId,
                            @Query("Owner") String ownerId,
                            @Query("Assign") String assignId,
                            @Query("Description") String description,
                            @Query("EnquiryNo") String enquiryNumber,
                            @Query("EnquiryN") String enquiryName,
                            @Query("Finance") String financierId,
                            @Query("FinanceType") String financeTypeId);

    @GET("api/enquiry")
    Call<List<EnquiryResponse>> getAdminEnquiryList();

    @GET("api/enquiry")
    Call<List<EnquiryResponse>> getAdminEnquiryPaginationList(@Query("id") String id,
                                                              @Query("pageNumber") int pageNumber,
                                                              @Query("pageSize") int pageSize);

    @GET("api/enquiry")
    Call<List<EnquiryResponse>> getEnquiryList(@Query("id") String id);

    @GET("/api/enquiry")
    Call<List<EnquiryResponse>> getEnquiryPaginationList(@Query("id") String id,
                                                         @Query("pageNumber") int pageNumber,
                                                         @Query("pageSize") int pageSize);

    @GET("Api/branch")
    Call<List<EnquiryMaxNumberResponse>> getEnquiryPrefixList(@Query("id") String branchId);

    @GET("Api/enqMax")
    Call<List<EnquiryMaxNumberResponse>> getEnquiryMaxNumberList(@Query("id") String branchId);

    @GET("Api/user")
    Call<List<EnquiryMaxNumberResponse>> getUserBranchId(@Query("id") String mobileNumber);

    @GET("Api/feedback")
    Call<List<FeedbackResponse>> getFeedbackList();

    @GET("Api/followup")
    Call<List<FollowupResponse>> getFollowupList();

    @POST("Api/Insert/EnquiryFollowup")
    Call<String> addEnquiryFollowUP(@Query("Owner") String Owner,
                                    @Query("EnquiryId") String enquiryId,
                                    @Query("FollowUp") String followUpId,
                                    @Query("Feedback") String feedBackId,
                                    @Query("Status") String statusId,
                                    @Query("Date") String dateTime,
                                    @Query("Details") String description);

    @GET("Api/enquiryfollowup")
    Call<List<FollowupResponse>> getFollowUpList(@Query("id") String enquiryId);

    @GET("Api/enquiryfollowup")
    Call<List<FollowupResponse>> getFollowUpPaginationList(@Query("id") String enquiryId,
                                                           @Query("pageNumber") int pageNumber,
                                                           @Query("pageSize") int pageSize);

    @GET("api/Quotation")
    Call<List<QuotationResponse>> getAdminQuotationList();

    @GET("api/Quotation")
    Call<List<QuotationResponse>> getAdminQuotationPaginationList(@Query("pageNumber") int pageNumber,
                                                                  @Query("pageSize") int pageSize);

    @GET("api/Quotation")
    Call<List<QuotationResponse>> getQuotationList(@Query("id") String id);

    @GET("api/Quotation")
    Call<List<QuotationResponse>> getQuotationPaginationList(@Query("id") String id,
                                                             @Query("pageNumber") int pageNumber,
                                                             @Query("pageSize") int pageSize);

    @GET("api/QuoMax")
    Call<List<EnquiryMaxNumberResponse>> getQuotationMaxNumberList();

    @POST("Api/Insert/Quotation")
    Call<String> addQuotation(@Query("Date") String date,
                              @Query("ContactId") String contactId,
                              @Query("ModelCat") String categoryId,
                              @Query("ModelName") String modelNameId,
                              @Query("Product") String productId,
                              @Query("ModelColor") String colorId,
                              @Query("PurchaseType") String purchaseNameId,
                              @Query("EnqType") String enquiryId,
                              @Query("CustomerType") String customerTypeId,
                              @Query("Branch") String branchId,
                              @Query("Source") String sourceId,
                              @Query("Status") String statusName,
                              @Query("Stage") String stageId,
                              @Query("Owner") String ownerId,
                              @Query("Assign") String assignId,
                              @Query("Description") String description,
                              @Query("QuotationNo") String enquiryNumber,
                              @Query("QuotationN") String enquiryNumberWithPrefix,
                              @Query("UnitPrice") String unitPrice,
                              @Query("Showroom") String Showroom,
                              @Query("Roadtax") String roadTax,
                              @Query("RSA") String roadOnSie,
                              @Query("AMC") String amc,
                              @Query("EW") String externallyWarranty,
                              @Query("Others") String Others,
                              @Query("Tax1") String taxType,
                              @Query("Tax2") String taxType2,
                              @Query("Cess") String cessType,
                              @Query("Percentage1") String taxPercentage1,
                              @Query("Percentage2") String taxPercentage2,
                              @Query("CPercentage") String cessPercentage,
                              @Query("Insurance") String insurance,
                              @Query("Accessories") String accessories,
                              @Query("Finance") String financierId,
                              @Query("FinanceType") String financeTypeId);

    @GET("Api/products")
    Call<List<ModelPriceResponse>> getModelPriceList(@Query("id") String variantId);

    @POST("Api/Insert/QuotationFollowup")
    Call<String> addQuotationFollowUP(@Query("Owner") String Owner,
                                      @Query("QuotationId") String enquiryId,
                                      @Query("FollowUp") String followUpId,
                                      @Query("Feedback") String feedBackId,
                                      @Query("Status") String statusId,
                                      @Query("Date") String dateTime,
                                      @Query("Details") String description);

    @GET("api/Quotationfollowup")
    Call<List<FollowupResponse>> getQuotationFollowUpList(@Query("id") String quotationId);

    @GET("api/Quotationfollowup")
    Call<List<FollowupResponse>> getQuotationFollowUpPaginationList(@Query("id") String quotationId,
                                                                    @Query("pageNumber") int pageNumber,
                                                                    @Query("pageSize") int pageSize);

    @POST("api/Contact/Insert")
    Call<String> addContact(@Query("Name") String contactPerson,
                            @Query("Phone") String contact,
                            @Query("MailId") String emailId,
                            @Query("Address") String companyAddress,
                            @Query("OwnerId") String userId,
                            @Query("assignid") String userId1);

    @GET("api/EWon")
    Call<List<CountResponse>> getEnquiryBookCount(@Query("id") String userId);

    @GET("api/ELost")
    Call<List<CountResponse>> getEnquiryLostCount(@Query("id") String userId);

    @GET("api/EOpen")
    Call<List<CountResponse>> getEnquiryOpenCount(@Query("id") String userId);

    @GET("api/EClose")
    Call<List<CountResponse>> getEnquiryCloseCount(@Query("id") String userId);

    @GET("api/ContactMax")
    Call<List<ContactResponse>> getContactMaxId(@Query("id") String userId);

    @GET("api/EnquiryDetails")
    Call<List<EnquiryResponse>> getEnquiryDetails(@Query("id") String enquiryId);

    @GET("api/EnqToday")
    Call<List<EnquiryResponse>> getTodayEnquiry(@Query("id") String userId);

    @GET("api/QuoToday")
    Call<List<QuotationResponse>> getTodayQuotation(@Query("id") String userId);

    @GET("api/EnqFupToday")
    Call<List<TodayFollowupResponse>> getTodayFollowup(@Query("id") String userId);

    @GET("api/QuoFupToday")
    Call<List<TodayFollowupResponse>> getTodayQuotationFollowup(@Query("id") String userId);

    @GET("api/QuoSearch")
    Call<List<QuotationResponse>> getFilterQuotationList(@Query("Mobile") String Mobile,
                                                         @Query("Category") String categoryId,
                                                         @Query("Name") String modelNameId,
                                                         @Query("varient") String variantId,
                                                         @Query("date") String fromDate,
                                                         @Query("date2") String toDate,
                                                         @Query("id") String userId);

    @GET("api/enqSearch")
    Call<List<EnquiryResponse>> getFilterEnquiryList(@Query("Mobile") String Mobile,
                                                     @Query("Category") String categoryId,
                                                     @Query("Name") String modelNameId,
                                                     @Query("varient") String variantId,
                                                     @Query("Status") String statusId,
                                                     @Query("date") String fromDate,
                                                     @Query("date2") String toDate,
                                                     @Query("id") String userId,
                                                     @Query("pageNumber") int pageNumber,
                                                     @Query("pageSize") int pageSize);

    @POST("api/Insert/EnquiryUpdate")
    Call<String> updateEnquiryStatus(@Query("EnquiryId") String enquiryId,
                                     @Query("Date") String date);


    @POST("api/Insert/EnqFupUpdate")
    Call<String> updateEnquiryFollowupStatus(@Query("EnqFupId") String enquiryId,
                                             @Query("Date") String toString);


    @POST("api/Insert/QuoFupUpdate")
    Call<String> updateQuotationFollowupStatus(@Query("QuoFupId") String enquiryId,
                                               @Query("Date") String toString);


    @GET("api/EnqFupMax")
    Call<List<FollowupResponse>> getLastEnquiryFollowup(@Query("id") String enquiryId);


    @GET("api/QuoFupMax")
    Call<List<FollowupResponse>> getLastQuotationFollowup(@Query("id") String enquiryId);


    @GET("api/EFupOpen")
    Call<List<CountResponse>> getActivityOpenCount(@Query("id") String enquiryId);


    @GET("api/EFupClose")
    Call<List<CountResponse>> getActivityTotalCount(@Query("id") String enquiryId);


    @GET("api/EFupTotal")
    Call<List<CountResponse>> getActivityCloseCount(@Query("id") String enquiryId);


    @POST("api/insert/EnqUpClose")
    Call<String> submitLostReason(@Query("Closure") String closerId,
                                  @Query("EnquiryId") String enquiryId,
                                  @Query("id") String userId,
                                  @Query("date") String date);


    @GET("api/Closure")
    Call<List<CategoryResponse>> getCloserList();


    @POST("Api/Update/Enquiry")
    Call<String> updateEnquiry(@Query("Date") String date,
                               @Query("Enqid") String enquiryId,
                               @Query("ContactId") String contactId,
                               @Query("ModelCat") String categoryId,
                               @Query("ModelName") String modelNameId,
                               @Query("Product") String productId,
                               @Query("ModelColor") String colorId,
                               @Query("PurchaseType") String purchaseNameId,
                               @Query("EnqType") String enquiryType,
                               @Query("CustomerType") String customerTypeId,
                               @Query("Branch") String branchId,
                               @Query("Source") String sourceId,
                               @Query("Status") String statusId,
                               @Query("Stage") String stageId,
                               @Query("Owner") String ownerId,
                               @Query("Assign") String assignId,
                               @Query("Description") String description,
                               @Query("ClosingDate") String closingDate,
                               @Query("ClosingType") String closingType,
                               @Query("Closure") String Closure,
                               @Query("Finance") String financierId,
                               @Query("FinanceType") String financeTypeId);

    @GET("Api/QuotationDetails")
    Call<List<QuotationResponse>> getQuotationDetails(@Query("id") String quotationId);

    @POST("Api/Update/Quotation")
    Call<String> updateQuotation(@Query("Quoid") String quotationId,
                                 @Query("Date") String date,
                                 @Query("ContactId") String contactId,
                                 @Query("ModelCat") String categoryId,
                                 @Query("ModelName") String modelNameId,
                                 @Query("Product") String productId,
                                 @Query("ModelColor") String colorId,
                                 @Query("PurchaseType") String purchaseNameId,
                                 @Query("EnqType") String enquiryId,
                                 @Query("CustomerType") String customerTypeId,
                                 @Query("Branch") String branchId,
                                 @Query("Source") String sourceId,
                                 @Query("Status") String statusName,
                                 @Query("Stage") String stageId,
                                 @Query("Owner") String ownerId,
                                 @Query("Assign") String assignId,
                                 @Query("Description") String description,
                                 @Query("QuotationNo") String enquiryNumber,
                                 @Query("QuotationN") String enquiryNumberWithPrefix,
                                 @Query("UnitPrice") String unitPrice,
                                 @Query("Showroom") String Showroom,
                                 @Query("Roadtax") String roadTax,
                                 @Query("RSA") String roadOnSie,
                                 @Query("AMC") String amc,
                                 @Query("EW") String externallyWarranty,
                                 @Query("Others") String Others,
                                 @Query("Tax1") String taxType,
                                 @Query("Tax2") String taxType2,
                                 @Query("Cess") String cessType,
                                 @Query("Percentage1") String taxPercentage1,
                                 @Query("Percentage2") String taxPercentage2,
                                 @Query("CPercentage") String cessPercentage,
                                 @Query("Insurance") String insurance,
                                 @Query("Accessories") String accessories,
                                 @Query("ClosingDate") String ClosingDate,
                                 @Query("ClosingType") String ClosingType,
                                 @Query("Closure") String Closure,
                                 @Query("Finance") String financierId,
                                 @Query("FinanceType") String financeTypeId);

    @GET("Api/Finance")
    Call<List<FinanceResponse>> getFinancierList();
}
