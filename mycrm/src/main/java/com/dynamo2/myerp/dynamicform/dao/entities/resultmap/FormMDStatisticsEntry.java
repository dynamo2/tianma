package com.dynamo2.myerp.dynamicform.dao.entities.resultmap;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 2/20/13
 * Time: 1:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class FormMDStatisticsEntry {
    @Id
    private Long formMetadataId;
    private Long newCount;
    private Long endCount;
    private Long proceeingCount;

    public Long getFormMetadataId() {
        return formMetadataId;
    }

    public Long getNewCount() {
        if(this.newCount == null)
            return 0L;

        return newCount;
    }

    public Long getEndCount() {
        if(this.endCount == null)
            return 0L;

        return endCount;
    }

    public Long getProceeingCount() {
        if(this.proceeingCount == null)
            return 0L;

        return proceeingCount;
    }

    public void setEndCount(Long endCount) {
        this.endCount = endCount;
    }

    public void setFormMetadataId(Long formMetadataId) {
        this.formMetadataId = formMetadataId;
    }

    public void setNewCount(Long newCount) {
        this.newCount = newCount;
    }

    public void setProceeingCount(Long proceeingCount) {
        this.proceeingCount = proceeingCount;
    }
}
