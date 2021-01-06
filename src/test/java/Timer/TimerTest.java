package Timer;

import Music.Systems.WorldBoxDisc;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Classe de test de la classe Timer
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimerTest {

    /**
     * Initialise les données nécessaires aux tests. Ici, les pistes sonores avant le début de tous les tests.
     */
    @Test
    @Order(1)
    public void init() {
        WorldBoxDisc.init();
    }

    /**
     * Teste l'ajout de temps à un timer
     */
    @Test
    @Order(2)
    public void testAddTime(){
        TimerController timer1 = new TimerController(60);
        TimerController oracle = new TimerController(120);
        timer1.bonusTime(60);

        assertEquals("Cette méthode devrait ajouter du temps à un ti mer.", timer1, oracle); //Il faut redefinir la méthode equals pour que cela marche
    }

    /**
     * Teste la diminution de temps à un timer
     */
    @Test
    @Order(3)
    public void testSubTime(){
        TimerController timer1 = new TimerController(60);
        TimerController oracle = new TimerController(30);
        timer1.penaltyTime(30);
        assertEquals("Cette méthode devrait soustraire du temps à un timer.", timer1, oracle);
    }

    /**
     * Teste qu'au bout d'un certain temps aléatoire, le timer est terminé
     */
    @Test
    @Order(4)
    public void testTimeRemaining(){
        Random generator = new Random();
        int clockTime = generator.nextInt(3)+3; //Temps de l'horloge varie entre 5 et 3s
        TimerController timer1 = new TimerController(clockTime+10);
        TimerController oracle = new TimerController(10);

        timer1.start();
        Timer.sleep(clockTime*1000 + 1000);
        assertEquals("Cette méthode devrait montrer qu'il reste 10s au temps du timer.", timer1.getTimeFullSeconds(), oracle.getTimeFullSeconds());
    }

}
