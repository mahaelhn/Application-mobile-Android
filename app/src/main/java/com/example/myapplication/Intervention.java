package com.example.myapplication;

public class Intervention {

        public int IdInterv;
        public  String NomClient ,Description , TitreInterv ,MobileClient, Date;
        public Intervention(int IdInterv ,String NomClient, String MobileClient, String TitreInterv , String Date ,String Description){
            this.IdInterv = IdInterv;
            this.NomClient= NomClient;
            this.TitreInterv = TitreInterv;
            this.MobileClient= MobileClient;
            this.Date = Date;
            this.Description= Description;
        }
        public String toString()
        { return IdInterv + "\n" +NomClient+ "\n" +TitreInterv+ "\n"+Date+"\n" +MobileClient+"\n"+Description; }
}
