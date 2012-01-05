/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dok.model;

/**
 *
 * @author Tomas
 */
    public class Result implements Comparable<Result> {
        int each;
        int oneach;
        int count;
        int allImage;
        int from;
        int allMessage;
        
        @Override
        public int compareTo(Result o) {
            if(o.count > this.count){
                return -1;
            }else{
                return 1;
            }
        }

    public Result(int oneach, int each,  int allImage,  int allMessage, int from) {
        this.each = each;
        this.oneach = oneach;
        this.allImage = allImage;
        this.from = from;
        this.allMessage = allMessage;
    }
        
        

        
        public Result(int count, int oneach, int each, int allImage, int allMessage, int from){
            this.count = count;
            this.each = each;
            this.oneach = oneach;
            this.allImage = allImage;
            this.allMessage = allMessage;
            this.from = from;
            System.out.println("for each: "+each+" from "+from+"pixel on color: "+oneach+" is "+count+ " changes");
        }

    public Result(int each, int oneach, int all, int from) {
        this.each = each;
        this.oneach = oneach;
        this.allImage = all;
        this.from = from;
    }
        
        

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEach() {
        return each;
    }

    public void setEach(int each) {
        this.each = each;
    }

    public int getColor() {
        return oneach;
    }

    public void setColor(int oneach) {
        this.oneach = oneach;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getAllImage() {
        return allImage;
    }

    public void setAllImage(int allImage) {
        this.allImage = allImage;
    }

    public int getAllMessage() {
        return allMessage;
    }

    public void setAllMessage(int allMessage) {
        this.allMessage = allMessage;
    }
        
        
        
        
    }
