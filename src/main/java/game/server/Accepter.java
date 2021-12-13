package game.server;

import java.util.ArrayDeque;

public class Accepter extends Thread{
    final ArrayDeque<Message> queue;

    public Accepter(){
        queue=new ArrayDeque<>();
    }


    public void submit(Message message){
        synchronized (queue){
            queue.push(message);
            if(queue.size()==1){
                queue.notify();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        while(!interrupted()){
            try{
                synchronized (queue) {
                    if (queue.size() == 0)
                        queue.wait();
                    queue.pop();
                }
            }catch (InterruptedException e){
                interrupt();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
