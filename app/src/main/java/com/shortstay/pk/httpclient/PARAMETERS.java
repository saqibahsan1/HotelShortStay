package com.shortstay.pk.httpclient;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@StringDef({PARAMETERS.EMAIL,
		PARAMETERS.PASSWORD,
		PARAMETERS.DEVICETOKEN,
		PARAMETERS.APITOKEN,
		PARAMETERS.TOTAL,
		PARAMETERS.SESSION_ID,
		PARAMETERS.ORDER_TYPE,
		PARAMETERS.DEVICETYPE,
		PARAMETERS.SINGLE_CHOICE,
		PARAMETERS.USERTYPE,
		PARAMETERS.PART_CODE,
		PARAMETERS.PART_NAME,
		PARAMETERS.USERNAME,
		PARAMETERS.NAME,
		PARAMETERS.SCREEN_NAME,
		PARAMETERS.PROFILE_IMAGE,
		PARAMETERS.ENGINE_NO,
		PARAMETERS.AMOUNT,
		PARAMETERS.REGISTRATION_NO,
		PARAMETERS.MODEL_ID,
		PARAMETERS.COLOR_ID,
		PARAMETERS.PURCHASE_DATE,
		PARAMETERS.CONTACT,
		PARAMETERS.NIC,
		PARAMETERS.CITY_ID,
		PARAMETERS.ADDRESS,
		PARAMETERS.DEALER_ID,
		PARAMETERS.DEALER_CODE,
		PARAMETERS.DISCOUNT,
		PARAMETERS.KM_READING,
		PARAMETERS.STAFF_ASSIGNED,
		PARAMETERS.VISIT_TYPE_ID,
		PARAMETERS.TOTAL_SERVICES,
		PARAMETERS.TOTAL_ACCESSORIES,
		PARAMETERS.OPENING_DATE,
		PARAMETERS.GRAND_TOTAL,
		PARAMETERS.SERVICES,
		PARAMETERS.ACCESSORIES,
		PARAMETERS.LATITUDE,
		PARAMETERS.DATA,
		PARAMETERS.DO_NUMBER,
		PARAMETERS.LONGITUDE,
		PARAMETERS.JOB_CARD_ID,
		PARAMETERS.DEVICE_ID,
		PARAMETERS.REFERENCE_ID,
		PARAMETERS.CAMPAIGN_ID,
		PARAMETERS.PARTS_DATA,
		PARAMETERS.SERVICES_FREE,
		PARAMETERS.ACCESSORIES_FREE,
		PARAMETERS.CUSTOMER_TYPE,
		PARAMETERS.FROM_DATE,
		PARAMETERS.TO_DATE,
		PARAMETERS.MULTI_CHOICE,
		PARAMETERS.DASHBOARD,
		PARAMETERS.LOGIN,
		PARAMETERS.DESCRIPTIVE,
		PARAMETERS.SCALE_OF_RATING,
		PARAMETERS.POST_SURVEY_ANSWER,
		PARAMETERS.OLD_PASSWORD,
		PARAMETERS.CALLING_PLAN_SURVEY,
		PARAMETERS.NEW_PASSWORD,
		PARAMETERS.SURVEY_ID,
		PARAMETERS.PHONE,
		PARAMETERS.FULL_NAME,
		PARAMETERS.POST_SURVEY_NOT_INTERESTED,
		PARAMETERS.REMARKS,
		PARAMETERS.ACTION,
		PARAMETERS.USER_ID,
		PARAMETERS.IS_ADMIN})
@Retention(RetentionPolicy.SOURCE)
public @interface PARAMETERS
	{
		String EMAIL = "email";
		String PHONE = "mobile";
		String LOGIN = "login";
		String USER_ID = "userid";
		String USER_NUMBER = "user_number";
		String USER_DATA = "users_data";
		String FORCE_LOGOUT = "force_logout";
		String ACTION = "action";
		String REQUEST_ID = "request_id";
		String SINGLE_CHOICE = "single_choice";
		String OLD_PASSWORD = "old_password";
		String POST_SURVEY_ANSWER = "post_survey_answer";
		String POST_SURVEY_NOT_INTERESTED = "post_survey_not_interested";
		String SHOW_MESSAGE_AFTER_CALL = "show_message_after_call";
		String SURVEY_ID = "surveyid";
		String SURVEY_ID2 = "survey_id";
		String CUSTOMER_ID = "customerid";
		String CUSTOMER_ID2 = "customer_id";
		String INSERT_CTR = "insert_cdr";
		String GET_USER_SURVEY_REPORT = "get_user_survey_report";
		String REMARKS = "remarks";
		String RESET_PASSWORD = "reset_password";
		String QUESTION_ANSWER = "question_answer";
		String NEW_PASSWORD = "new_password";
		String MULTI_CHOICE = "multi_choice";
		String DESCRIPTIVE = "descriptive";
		String SCALE_OF_RATING = "scale_of_rating";
		String DASHBOARD = "dashboard";
		String VERSION_CHECK = "version_check";
		String CALLING_PLAN = "get_calling_plan";
		String CALLING_PLAN_SURVEY = "get_calling_plan_survey";
		String GET_SURVEY = "get_survey";
		String TOTAL = "total";
		String PASSWORD = "password";
		String PAYMENT_STATUS = "payment_status";
		String NEXT_VISIT_DATE = "next_visit_date";
		String DEVICETOKEN = "android_push_id";
		String APITOKEN = "api_token";
		String DATA = "data";
		String DEVICETYPE = "Android";
		String USERTYPE = "type";
		String USERNAME = "username";
		String NAME = "name";
		String FULL_NAME = "fullName";
		String FIRST_NAME = "firstname";
		String LAST_NAME = "lastname";
		String DOB = "dob";
		String API_TOKEN = "api_token";
		String ORDER_TYPE = "order_type";
		String PART_CODE = "part_code";
		String PART_NAME = "part_name";
		String CHASSIS_NO = "chassis_no";
		String ENGINE_NO = "engine_no";
		String REGISTRATION_NO = "registration_no";
		String MODEL_ID = "model_id";
		String COLOR_ID = "color_id";
		String PURCHASE_DATE = "purchase_date";
		String CONTACT = "contact";
		String NIC = "nic";
		String CITY_ID = "city_id";
		String ADDRESS = "address";
		String CITY = "city";
		String DEALER_ID = "dealer_id";
		String CUSTOMER_NAME = "customer_name";
		String CUSTOMER_NUMBER = "customer_number";
		String CUSTOMER_TYPE = "type";
		String DEALER_CODE = "dealer_code";
		String FROM_DATE = "from_date";
		String TO_DATE = "to_date";
		String DO_NUMBER = "do_number";
		String REFERENCE_ID = "reference_ids";
		String DISCOUNT = "discount";
		String AMOUNT = "amount";
		String KM_READING = "km_reading";
		String CAMPAIGN_ID = "campaign_id";
		String STAFF_ASSIGNED = "staff_assigned";
		String VISIT_TYPE_ID = "visit_type_id";
		String TOTAL_SERVICES = "total_services";
		String TOTAL_ACCESSORIES = "total_accessories";
		String OPENING_DATE = "opening_date";
		String GRAND_TOTAL = "grand_total";
		String SERVICES = "services";
		String SERVICES_FREE = "services_free";
		String ACCESSORIES_FREE = "accessories_free";
		String ACCESSORIES = "accessories";
		String LATITUDE = "latitude";
		String LONGITUDE = "longitude";
		String APP_REF_NUMBER = "app_ref_number";
		String JOB_CARD_ID = "job_card_id";
		String PARTS_DATA = "parts_data";


		String SCREEN_NAME = "screen_name";
		String PROFILE_IMAGE = "profile_image";
		String DEVICE_ID = "device_id";
		String IS_ADMIN = "is_admin";
		String ACCESS_CODE = "code";
		String SESSION_ID = "session_id";
		String COMPANY_ID = "company_id";
		String MOBILE = "mobile";
		String COMPANY = "company";
		String WHICH = "which";
		String POSITION = "position";
		String QUIZ_ID = "quiz_id";
	}
