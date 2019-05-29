package com.example.rightprice;

class Vehicle {
        public int getLat() {
                return lat;
        }

        public void setLat(int lat) {
                this.lat = lat;
        }

        public int getLng() {
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

        public int getStartPrice() {
                return startPrice;
        }

        public void setStartPrice(int startPrice) {
                this.startPrice = startPrice;
        }

        public int getMinutePrice() {
                return minutePrice;
        }

        public void setMinutePrice(int minutePrice) {
                this.minutePrice = minutePrice;
        }

        public int getTimeLimit() {
                return timeLimit;
        }

        public void setTimeLimit(int timeLimit) {
                this.timeLimit = timeLimit;
        }

        private int lat;
        private int lng;
        private String vendor;
        private String id;
        private int battery;
        private int startPrice;
        private int minutePrice;
        private int timeLimit;



}
