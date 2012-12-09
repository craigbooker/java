package com.eatrightapp.external.era;

public class RestaurantInfo {
        private String id;
        private boolean franchise;
        private String franchiseId;
        private boolean flagged;
        
        public RestaurantInfo() {
                
        }
        
        public RestaurantInfo(String id, boolean franchise, String franchiseId, boolean flagged) {
                setId(id);
                setFranchise(franchise);
                setFranchiseId(franchiseId);
                setFlagged(flagged);
        }

        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public boolean isFranchise() {
                return franchise;
        }

        public void setFranchise(boolean franchise) {
                this.franchise = franchise;
        }

        public String getFranchiseId() {
                return franchiseId;
        }

        public void setFranchiseId(String franchiseId) {
                this.franchiseId = franchiseId;
        }

        public boolean isFlagged() {
                return flagged;
        }

        public void setFlagged(boolean flagged) {
                this.flagged = flagged;
        }

        public String toString() {
                StringBuffer sb = new StringBuffer();
                sb.append(getClass().getName() + ": ");
                sb.append("id=\"").append(getId()).append("\", ");
                sb.append("franchise=").append(isFranchise()).append(", ");
                sb.append("franchiseId=\"").append(getFranchiseId()).append("\", ");
                sb.append("flagged=").append(isFlagged()).append(";");
                return sb.toString();
        }
        
        
}