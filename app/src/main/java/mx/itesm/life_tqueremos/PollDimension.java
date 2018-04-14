package mx.itesm.life_tqueremos;

/**
 * Created by jawn on 14/04/18.
 */

public class PollDimension {
    private String title;
    private boolean done;

    PollDimension(){}

    PollDimension(String title, boolean done){
        this.title = title;
        this.done = done;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
