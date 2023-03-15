package util.logger;

import java.io.*;

public class ObserverFile extends Observer{

    private String path;

    private PrintWriter out = null;

    protected String getThePathToInit(){
        String path = "src/output";

        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        return absolutePath;
    }

    public ObserverFile(AbstractSubject subject, String fileName){
        this.subject = subject;
        this.subject.attach(this);
        this.path = fileName;


    }

    @Override
    public void update() {
        try {
            out = new PrintWriter(new OutputStreamWriter(new FileOutputStream( this.getThePathToInit() + "/"+ this.path, true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.print(subject.getState());
        out.flush();
        out.close();
    }
}
