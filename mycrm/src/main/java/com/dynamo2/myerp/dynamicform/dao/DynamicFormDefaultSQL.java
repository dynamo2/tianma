package com.dynamo2.myerp.dynamicform.dao;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/16/13
 * Time: 8:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class DynamicFormDefaultSQL {
    public static String BASIC_NATIVE_SQL = "select d.customer_id customerId, d.summary, d.form_biz_id formBizId, d.id, d.status, d.created_by createdBy, d.created, d.last_modified lastModified, d.last_modified_by lastModifiedBy, d.assignee_account assigneeAccount, f.form_label formLabel"
            + " from dynamic_form_default d left join form_metadata f on (d.form_metadata_id=f.id) "
            + " where (d.status<>'DELETED') and (d.form_metadata_id = ?) ";

    public static String DEFAULT_ACCOUNT_CRITERIA = " (d.created_by in (:createBy) or d.last_modified_by in (:modifiedBy) or d.assignee_account in (:assigneeAccount)) ";
    public static String AGENT_CRITERIA = " (d.customer_id in (select id from customer where customer_id=:customerId)) ";
    public static String DEFAULT_ORDER_BY = " order by d.last_modified desc";

    public static String listBelongedFormsToMe(){
        return BASIC_NATIVE_SQL + " and " + DEFAULT_ACCOUNT_CRITERIA
                + DEFAULT_ORDER_BY;
    }
}
