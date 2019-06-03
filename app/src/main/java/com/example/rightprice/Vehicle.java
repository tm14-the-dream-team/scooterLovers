package com.example.rightprice;

class Vehicle {
        public double getLat() {
                return lat;
        }

        public void setLat(double lat) {
                this.lat = lat;
        }

        public double getLng() {
                return lng;
        }

        public void setLng(double lng) {
                this.lng = lng;
        }

        public String getVendor() {
                return vendor;
        }

        public void setVendor(String vendor) {
                this.vendor = vendor;
        }

        public int getBattery() {
                return battery;
        }

        public void setBattery(int battery) {
                this.battery = battery;
        }

        public String getMPrice() {
                return Mprice;
        }

        public void setMPrice(String Mprice) {
                this.Mprice = Mprice;
        }

        public String getSPrice() {
        return Sprice;
    }

        public void setSPrice(String Mprice) {
        this.Sprice = Sprice;
    }







        private double lat;
        private double lng;
        private String vendor;
        private String id;
        private String type;
        private int battery;
        private String Sprice;
        private String Mprice;

        public String toString(){
                return "Vendor: "+vendor+", id: "+id+", location: ("+lat+","+lng+"), battery: "+battery+" Price: "+Sprice;
        }

        public Vehicle(String vendor, String id, int battery, double lat, double lng, String Mprice,String Sprice){
                this.vendor = vendor;
                this.id = id;
                this.battery = battery;
                this.lat = lat;
                this.lng = lng;
                this.Sprice = Sprice;
                this.Mprice = Mprice;


        }


        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}
