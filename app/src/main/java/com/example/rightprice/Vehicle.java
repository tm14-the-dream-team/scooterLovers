package com.example.rightprice;

class Vehicle {
        public double getLat() {
                return lat;
        }

        public void setLat(int lat) {
                this.lat = lat;
        }

        public double getLng() {
                return lng;
        }

        public void setLng(int lng) {
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

        public double getStartPrice() {
                return startPrice;
        }

        public void setStartPrice(double startPrice) {
                this.startPrice = startPrice;
        }

        public double getMinutePrice() {
                return minutePrice;
        }

        public void setMinutePrice(double minutePrice) {
                this.minutePrice = minutePrice;
        }

        public int getTimeLimit() {
                return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
                this.timeLimit = timeLimit;
        }

        private double lat;
        private double lng;
        private String vendor;
        private String id;
        private int battery;
        private double startPrice;
        private double minutePrice;
        private int timeLimit;

        public String toString(){
                return "Vendor: "+vendor+", id: "+id+", location: ("+lat+","+lng+"), battery: "+battery+" Price: "+startPrice+" to start & "+ minutePrice+ " per minute";
        }

        public Vehicle(String vendor, String id, int battery, double lat, double lng, double startPrice, double minutePrice){
                this.vendor = vendor;
                this.id = id;
                this.battery = battery;
                this.lat = lat;
                this.lng = lng;
                this.startPrice = startPrice;
                this.minutePrice = minutePrice;


        }



}
