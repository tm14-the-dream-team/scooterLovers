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




        private double lat;
        private double lng;
        private String vendor;
        private String id;
        private String type;
        private int battery;
        private String price;

        public String toString(){
                return "Vendor: "+vendor+", id: "+id+", location: ("+lat+","+lng+"), battery: "+battery+" Price: "+price;
        }

        public Vehicle(String vendor, String id, int battery, double lat, double lng, String price){
                this.vendor = vendor;
                this.id = id;
                this.battery = battery;
                this.lat = lat;
                this.lng = lng;
                this.price = price;


        }


        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }
}
