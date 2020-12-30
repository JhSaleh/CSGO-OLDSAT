package Timer;

import Music.Systems.Son;
import Music.Systems.WorldBoxDisc;
import com.sun.javafx.animation.TickCalculation;

public class Timer extends Thread {
    private int tempsSecondes; //donne en seconde
    private volatile boolean stateTimer;
    private volatile Lock lock;
    private int tickTime;

    public Timer(int tempsSecondes, Lock inLock){
        this.tempsSecondes = tempsSecondes;
        this.stateTimer = false; //par défaut, le timer est a l'arret
        this.lock = inLock;
        this.tickTime = 60; //par defaut, le son de tick se déclenche sur la dernière minute
    }

    public static void sleep(int ms){
        try{
            Thread.sleep(ms);
        } catch ( InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * Retire du temps du timer
     * @param secondes
     */
    public synchronized void substractTime(int secondes){ //Exclusion mutuelle requise : le thread peut tourner & la méthode substractTime peut être appelé depuis le main
        this.tempsSecondes -= secondes;
    }

    /**
     * Ajoute du temps au timer
     * @param secondes
     */
    public synchronized  void addTime(int secondes){
        this.tempsSecondes += secondes;
    }

    /**
     * Retourne le nombre total de secondes du timer
     * @return Integer
     */
    public synchronized int getTimeFullSeconds(){
        return tempsSecondes;
    }

    /**
     * Retourne le nombre de minutes restantes du timer
     * @return Integer
     */
    public synchronized int getRemainingMin(){
        return  (int) tempsSecondes/60;
    }

    /**
     * Retourne le nombre de secondes restantes modulo 60 du timer
     * @return Integer
     */
    public synchronized int getRemainingSec(){
        return tempsSecondes%60;
    }

    /**
     * Renvoit le temps restant sous le format string mm:ss
     * @return String
     */
    public synchronized String getRemainingTime(){
        if(getRemainingSec() > 9) {
            return (String) (getRemainingMin() + ":" + getRemainingSec());
        } else {
            return (String) (getRemainingMin() + ":0" + getRemainingSec());
        }
    }

    /**
     * Active/Desactive le timer
     */
    public synchronized void toogleTimer(){
        if(stateTimer){
            stateTimer = false;
        } else {
            stateTimer = true;
            goTimer();
        }
    }

    /**
     * Endort le thread du timer
     */
    public void waitTimer(){
        try {
            synchronized (lock) { //obtention du lock/objet lock
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Redemarre le thread du timer
     */
    public void goTimer(){
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * Modifie le seuil de tick limite, i.e. la limite de secondes à partir duquelle on entends les ticks de l'horloge
     * @param inTickTime
     */
    public void setTickTime(int inTickTime){
        if(inTickTime >= 10){
            this.tickTime = inTickTime;
        } else {
            System.out.println("Niveau de tick trop bas.");
        }
    }

    @Override
    public void run() {
        while (this.tempsSecondes > 0){
            if(stateTimer) {
                sleep(1000);
                substractTime(1);
                if(this.tempsSecondes < tickTime) { //Passer un certain, on entend un tick signalant que c'est bientot la fin
                    WorldBoxDisc.play(Son.tick);
                }
                System.out.println(getRemainingTime()); //a enlever
            } else {
                waitTimer(); //bloque le tread pour économiser le cpu
            }
        }
    }


}