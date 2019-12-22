/*
 * Helper Class from http://kuldeeptyagi.blogspot.com/2011/05/salesforce-work-around-to-overcome-1000.html
 *
 * This helps the Wizard support up to 20,000 users by circumventing the Visualforce collection limit of 1000 elements.
 */

public with sharing class PaginatedSelectList {

      private List<SelectOption> selectList=new List<SelectOption>();
      private final Integer SUB_LIST_SIZE=1000;

      //Returns total numbers of items.
      public Integer getSize(){
            return selectList.size();
      }

      //Add SelectOption.
      public void add(SelectOption so){
                  selectList.add(so);
      }

      //Gets SelectOption at given index.
      public SelectOption get(Integer index){
            if(index>-1 && index<getSize()){
                  return selectList.get(index);
            }else{
                  return null;
            }    
      }

      //Removes SelectOption at given index
      public void remove(Integer index){
            if(index>-1 && index<getSize()){
                  selectList.remove(index);
            }    
      }

      public void clear(){
            selectList.clear();
      }

      public List<SelectOption> getSelectList(){
            return selectList;
      }

      //Gets Select Options for the given start and end index both are inclusive.
      public List<SelectOption> getSubList(Integer startIndex,Integer endIndex){
            List<SelectOption> subList=new List<SelectOption>();
            if(startIndex>-1 && startIndex<selectList.size()&& endIndex>-1 && endIndex<selectList.size()&&startIndex<=endIndex){
                  for(Integer i=startIndex;i<=endIndex;i++){
                        subList.add(get(i));
                  }
            }
            return subList;
      }

      //Gets Nth sub list by dividing the main list in sublists of SUB_LIST_SIZE(1000 elements).
      public List<SelectOption>getNthSubList(Integer index){
            Integer subListCount=(Integer)(selectList.size()/SUB_LIST_SIZE);
            if(Math.mod(selectList.size(),SUB_LIST_SIZE)>0) subListCount++;
            if(index>=0 && index<=subListCount){
                  Integer startIndex=index*SUB_LIST_SIZE;
                  Integer endIndex=index*SUB_LIST_SIZE+(SUB_LIST_SIZE-1);
                  if(endIndex>=selectList.size())endIndex=selectList.size()-1;
                  return getSubList(startIndex,endIndex);
            }else{
                  return new List<SelectOption>();
            }
      }
      
      //Gets sublist for given index.
      public List<SelectOption>get0(){
            return getNthSubList(0);
      }

      public List<SelectOption>get1(){
            return getNthSubList(1);
      }

      public List<SelectOption>get2(){
            return getNthSubList(2);
      }

      public List<SelectOption>get3(){
            return getNthSubList(3);
      }

      public List<SelectOption>get4(){
            return getNthSubList(4);
      }

      public List<SelectOption>get5(){
            return getNthSubList(5);
      }

      public List<SelectOption>get6(){
            return getNthSubList(6);
      }

      public List<SelectOption>get7(){
            return getNthSubList(7);
      }

      public List<SelectOption>get8(){
            return getNthSubList(8);
      }

      public List<SelectOption>get9(){
            return getNthSubList(9);
      }

      public List<SelectOption>get10(){
            return getNthSubList(10);
      }

      public List<SelectOption>get11(){
            return getNthSubList(11);
      }

      public List<SelectOption>get12(){
            return getNthSubList(12);
      }

      public List<SelectOption>get13(){
            return getNthSubList(13);
      }

      public List<SelectOption>get14(){
            return getNthSubList(14);
      }

      public List<SelectOption>get15(){
            return getNthSubList(15);
      }

      public List<SelectOption>get16(){
            return getNthSubList(16);
      }

      public List<SelectOption>get17(){
            return getNthSubList(17);
      }

      public List<SelectOption>get18(){
            return getNthSubList(18);
      }

      public List<SelectOption>get19(){
            return getNthSubList(19);
      }

    static testMethod void testPaginatedSelectList() {
        PaginatedSelectList myList = new PaginatedSelectList();
        myList.add(new SelectOption('One', 'One'));
        myList.add(new SelectOption('Two', 'Two'));
        System.assertEquals(2, myList.getSize());
        System.assertEquals(2, myList.getSelectList().size());
        System.assertEquals(1, myList.getSubList(1, 1).size());
        System.assertEquals(2, myList.get0().size());
        System.assertEquals(0, myList.get1().size());
        System.assertEquals(0, myList.get2().size());
        System.assertEquals(0, myList.get3().size());
        System.assertEquals(0, myList.get4().size());
        System.assertEquals(0, myList.get5().size());
        System.assertEquals(0, myList.get6().size());
        System.assertEquals(0, myList.get7().size());
        System.assertEquals(0, myList.get8().size());
        System.assertEquals(0, myList.get9().size());
        System.assertEquals(0, myList.get10().size());
        System.assertEquals(0, myList.get11().size());
        System.assertEquals(0, myList.get12().size());
        System.assertEquals(0, myList.get13().size());
        System.assertEquals(0, myList.get14().size());
        System.assertEquals(0, myList.get15().size());
        System.assertEquals(0, myList.get16().size());
        System.assertEquals(0, myList.get17().size());
        System.assertEquals(0, myList.get18().size());
        System.assertEquals(0, myList.get19().size());
        myList.remove(1);
        System.assertEquals(1, myList.getSize());
        myList.clear();    
        System.assertEquals(0, myList.getSize());
    }
 }