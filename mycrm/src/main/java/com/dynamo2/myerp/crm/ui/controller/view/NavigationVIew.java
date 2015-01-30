package com.dynamo2.myerp.crm.ui.controller.view;

import com.dynamo2.myerp.crm.dao.entities.NavCategory;
import com.dynamo2.myerp.crm.dao.entities.NavForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wmx
 * Date: 3/28/13
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationVIew {
    private List<NavCategory> navCategories;
    private List<NavForm> navForms;

    public NavigationVIew(){

    }

    public class NavCategoryView implements Comparable{
        private NavCategory navCategory;
        private List<NavFormView> formViews = new ArrayList<NavFormView>();

        public NavCategoryView(NavCategory nc){
            this.navCategory = nc;
        }

        public List<NavFormView> getFormViews() {
            return formViews;
        }

        public void addFormView(NavForm nf){

        }

        //@Override
        public int compareTo(Object o) {
            if(o == null || !(o instanceof NavCategoryView)){
                return -1;
            }

            return ((NavCategoryView)o).navCategory.getSeqNumber()-this.navCategory.getSeqNumber();  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public class NavFormView {
        private NavForm navForm;
    }
}
