package com.dynamo2.myerp.dynamicform.ui.view;

import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/1/13
 * Time: 4:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class GroupView {
    private List<Group> groups = new ArrayList<Group>();
    private Map<Integer, Group> groupNumberMap = new HashMap<Integer, Group>();

    public GroupView(List<FormFieldMetadata> formFields) {
        this.initGroups(formFields);
    }

    public List<Group> getGroups() {
        Collections.sort(groups);
        return groups;
    }

    public void reloadGroups(List<FormFieldMetadata> formFields) {
        this.groups = null;
        this.groupNumberMap = null;

        this.initGroups(formFields);
    }

    private void initGroups(List<FormFieldMetadata> formFields) {
        if (groups == null) {
            groups = new ArrayList<Group>();
        }

        if (this.groupNumberMap == null) {
            groupNumberMap = new HashMap<Integer, Group>();
        }

        if(CollectionUtils.isEmpty(formFields)){
            return;
        }

        Group group = null;
        Row row = null;
        for (FormFieldMetadata field : formFields) {
            if(field.getReferToFormMetadataId() != null){
                continue;
            }

            Integer groupNumber = field.getDisplayPositionGroup();
            Integer rowNumber = field.getDisplayPositionRow();

            group = this.getGroupOrNew(groupNumber);
            row = group.getRowOrNew(rowNumber);

            if (field.getIsLabel()) {
                group.setLabel(field.getFieldLabel());
            } else {
                row.addColumn(field);
            }
        }
    }

    private Group getGroupOrNew(int groupNumber) {
        if (!this.hasGroup(groupNumber)) {
            Group g = new Group(groupNumber);
            this.groupNumberMap.put(groupNumber, g);
            this.groups.add(g);
        }

        return this.groupNumberMap.get(groupNumber);
    }

    private boolean hasGroup(int groupNumber) {
        return this.groupNumberMap.containsKey(groupNumber);
    }

    public class Group implements Comparable {
        private String label;
        private Integer number;
        private List<Row> rows = new ArrayList<Row>();
        private Map<Integer, Row> rowNumberMap = new HashMap<Integer, Row>();

        public Group() {
        }

        public Group(int n) {
            this.number = n;
        }

        public List<Row> getRows() {
            Collections.sort(rows);
            return rows;
        }

        public Row getRow(int rowNumber) {
            return this.rowNumberMap.get(rowNumber);
        }

        public Row getRowOrNew(int rowNumber) {
            if (!this.hasRow(rowNumber)) {
                Row r = new Row(rowNumber);

                this.rowNumberMap.put(rowNumber, r);
                this.rows.add(r);
            }

            return this.rowNumberMap.get(rowNumber);
        }

        public boolean hasRow(int rowNumber) {
            return this.rowNumberMap.containsKey(rowNumber);
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getNumber() {
            return number;
        }

        public Map<Integer, Row> getRowNumberMap() {
            return rowNumberMap;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof Group)) {
                return false;
            }

            Group t = (Group) o;
            return this.getNumber() == t.getNumber();
        }

        public int hashCode() {
            return this.getNumber().hashCode() * 7 + 1000;
        }

        //@Override
        public int compareTo(Object o) {
            if (o == null || !(o instanceof Group)) {
                return -1;
            }

            Group t = (Group) o;
            return this.getNumber() - t.getNumber();
        }
    }

    public class Row implements Comparable {
        private Integer number;
        private List<Column> columns = new ArrayList<Column>();

        public Row(Integer number) {
            this.number = number;
        }

        public List<Column> getColumns() {
            return columns;
        }

        public void addColumn(FormFieldMetadata field) {
            this.columns.add(new Column(field));
        }

        public Integer getNumber() {
            return number;
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof Row)) {
                return false;
            }

            Row t = (Row) o;
            return this.getNumber() == t.getNumber();
        }

        public int hashCode() {
            return this.getNumber().hashCode() * 7 + 2000;
        }

        //@Override
        public int compareTo(Object o) {
            if (o == null || !(o instanceof Row)) {
                return -1;
            }

            Row t = (Row) o;
            return this.getNumber() - t.getNumber();
        }
    }

    public class Column {
        private FormFieldMetadata field;

        private boolean isInt;
        private boolean isDouble;
        private boolean isExpression;
        private boolean isInputText;
        private boolean isBigText;
        private boolean isTextArea;
        private boolean isSelectOneMenu;
        private boolean isDatetime;
        private boolean isReference;

        public Column(FormFieldMetadata field) {
            this.field = field;
            this.initStatus();
        }

        public FormFieldMetadata getField() {
            return field;
        }

        public boolean getIsBigText() {
            return isBigText;
        }

        public boolean getIsDatetime() {
            return isDatetime;
        }

        public boolean getIsDouble() {
            return isDouble;
        }

        public boolean getIsExpression() {
            return isExpression;
        }

        public boolean getIsInputText() {
            return isInputText;
        }

        public boolean getIsInt() {
            return isInt;
        }

        public boolean getIsReference() {
            return isReference;
        }

        public boolean getIsSelectOneMenu() {
            return isSelectOneMenu;
        }

        public boolean getIsTextArea() {
            return isTextArea;
        }

        public boolean getIsRequired(){
            return field.getIsRequired();
        }

        public String getColumnName(){
            return field.getColumnName();
        }

        public int getInputColumns(){
            return field.getInputColumns();
        }

        public int getInputRows(){
            return field.getInputRows();
        }

        private void initStatus(){
            this.resetStatus();

            if(!StringUtils.isEmpty(field.getExpression())){
                this.isExpression = true;
                return;
            }

            if(this.field.getReferToFormMetadataId() != null){
                this.isReference = true;
                return;
            }

            if(this.isType("double")){
                this.isDouble = true;
                return;
            }

            if(this.isType("int")){
                this.isInt = true;
                return;
            }

            if(this.isType("datetime")){
                this.isDatetime = true;
                return;
            }

            if(this.isType("text")){
                this.isTextArea = true;
                return;
            }

            if(this.isType("varchar")){
                if(this.field.getInputRows() > 1){
                    this.isBigText = true;
                    return;
                }else if(field.getInputRows() == 1){
                    if(!StringUtils.isEmpty(field.getOptionValues())){
                        this.isSelectOneMenu = true;
                        return;
                    }

                    this.isInputText = true;
                }
            }
        }

        private boolean isType(String typeName){
            return this.field.getFieldType().equals(typeName);
        }

        private void resetStatus(){
            isInt = false;
            isDouble = false;
            isExpression = false;
            isInputText = false;
            isBigText = false;
            isTextArea = false;
            isSelectOneMenu = false;
            isDatetime = false;
            this.isReference = false;
        }
    }
}
