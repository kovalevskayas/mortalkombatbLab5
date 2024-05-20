/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
package edu.mephi;

// ADD IMAGE!!!
import edu.mephi.enemys.ShaoKahn;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 *
 * @author Мария
 */
public class Fight {

  private int enemy_num = 1;
  private int location_num = 1;
  private int current_location = 1;

  ChangeTexts change = new ChangeTexts();
  int kind_attack[] = {0};
  int experiences[] = {40, 90, 180, 260, 410};
  EnemyFabric fabric = new EnemyFabric();
  int i = 1;
  int k = -1;
  int stun = 0;
  double v = 0.0;

  public int getEnemy_num() { return enemy_num; }

  public void setEnemy_num(int enemy_num) { this.enemy_num = enemy_num; }

  public int getCurrent_location() { return current_location; }

  public void setCurrent_location(int current_location) {
    this.current_location = current_location;
  }

  public int getLocation_num() { return location_num; }

  public void setLocation_num(int location_num) {
    this.location_num = location_num;
  }

  public void Move(Player p1, Player p2, JLabel l, JLabel l2) {
    if (stun == 1) {
      p1.setAttack(-1);
    }
    switch (Integer.toString(p1.getAttack()) +
            Integer.toString(p2.getAttack())) {
    case "10":
      v = Math.random();
      if (p1 instanceof ShaoKahn & v < 0.15) {
        p2.setHealth(-(int)(p1.getDamage() * 0.5));
        l2.setText("Your block is broken");

      } else {
        p1.setHealth(-(int)(p2.getDamage() * 0.5));
        l2.setText(p2.getName() + " counterattacked");
      }
      break;
    case "11":
      p2.setHealth(-p1.getDamage());
      l2.setText(p1.getName() + " attacked");
      break;
    case "00":
      v = Math.random();
      if (v <= 0.5) {
        stun = 1;
      }
      l2.setText("Both defended themselves");
      break;
    case "01":
      l2.setText(p1.getName() + " didn't attacked");
      break;
    case "-10":
      l.setText(p1.getName() + " was stunned");
      stun = 0;
      l2.setText(p2.getName() + " didn't attacked");
      break;
    case "-11":
      p1.setHealth(-p2.getDamage());
      l.setText(p1.getName() + " was stunned");
      stun = 0;
      l2.setText(p2.getName() + " attacked");
      break;
    }
  }

  public void Hit(Player human, Player enemy, int a, JLabel label,
                  JLabel label2, JDialog dialog, JLabel label3,
                  CharacterAction action, JProgressBar pr1, JProgressBar pr2,
                  JDialog dialog1, JDialog dialog2, JFrame frame,
                  ArrayList<Result> results, JLabel label4, JLabel label5,
                  JLabel label6, JLabel label7, JLabel label8, Items[] items,
                  JRadioButton rb) {
    label7.setText("");
    human.setAttack(a);

    if (k < kind_attack.length - 1) {
      k++;
    } else {
      kind_attack = action.ChooseBehavior(enemy, action);
      k = 0;
    }
    enemy.setAttack(kind_attack[k]);
    if (i % 2 == 1) {
      Move(human, enemy, label7, label8);
    } else {
      if (enemy instanceof ShaoKahn) {
        Boolean ret = healShaKahn(enemy, human, label7, label8);
        if (ret)
          Move(enemy, human, label7, label8);
      } else
        Move(enemy, human, label7, label8);
    }
    i++;
    change.RoundTexts(human, enemy, label, label2, i, label6);
    action.HP(human, pr1);
    action.HP(enemy, pr2);
    if (human.getHealth() <= 0 & items[2].getCount() > 0) {
      human.setNewHealth((int)(human.getMaxHealth() * 0.05));
      items[2].setCount(-1);
      action.HP(human, pr1);
      label2.setText(human.getHealth() + "/" + human.getMaxHealth());
      rb.setText(items[2].getName() + ", " + items[2].getCount() + " шт");
      label7.setText("Вы воскресли");
    }
    if (human.getHealth() <= 0 | enemy.getHealth() <= 0) {
      enemy_num--;
      if (enemy_num == -1 && location_num == current_location) {
        EndFinalRound(((Human)human), action, results, dialog1, dialog2, frame,
                      label4, label5);
      } else {
        EndRound(human, enemy, dialog, label3, action, items);
      }
    }
  }

  private Boolean healShaKahn(Player enemy, Player human, JLabel label7,
                              JLabel label8) {

    double v = Math.random();
    if (v < 0.5)
      return true;
    if (human.getAttack() == 1) {
      label8.setText(human.getName() + " double attacked!");
      enemy.setHealth(-(int)human.getDamage() * 2);
    } else
      label8.setText(enemy.getName() + " healed!");
    enemy.setHealth((int)((enemy.getMaxHealth() - enemy.getHealth()) * 0.5));
    return false;
  }

  public void EndRound(Player human, Player enemy, JDialog dialog, JLabel label,
                       CharacterAction action, Items[] items) {

    dialog.setVisible(true);
    dialog.setBounds(300, 150, 700, 600);
    if (human.getHealth() > 0) {
      label.setText("You win");
      ((Human)human).setWin();

      if (enemy instanceof ShaoKahn) {
        action.AddItems(38, 23, 8, items);
        action.AddPointsBoss(((Human)human), action.getEnemyes());
      } else {
        action.AddItems(25, 15, 5, items);
        action.AddPoints(((Human)human), action.getEnemyes(), false);
      }
    } else {
      label.setText(enemy.getName() + " win");
    }

    i = 1;
    k = -1;
    kind_attack = ResetAttack();
  }

  public void EndFinalRound(Human human, CharacterAction action,
                            ArrayList<Result> results, JDialog dialog1,
                            JDialog dialog2, JFrame frame, JLabel label1,
                            JLabel label2) {
    String text = "Победа не на вашей стороне";
    if (human.getHealth() > 0) {
      human.setWin();
      action.AddPoints(human, action.getEnemyes(), true);
      text = "Победа на вашей стороне";
    }
    boolean top = false;
    if (results == null) {
      top = true;
    } else {
      int i = 0;
      for (int j = 0; j < results.size(); j++) {
        if (human.getPoints() < results.get(j).getPoints()) {
          i++;
        }
      }
      if (i < 10) {
        top = true;
      }
    }
    if (top) {
      dialog1.setVisible(true);
      dialog1.setBounds(150, 150, 600, 500);
      label1.setText(text);
    } else {
      dialog2.setVisible(true);
      dialog2.setBounds(150, 150, 470, 360);
      label2.setText(text);
    }
    frame.dispose();
  }

  public int[] ResetAttack() {
    int a[] = {0};
    return a;
  }

  public Player NewRound(Player human, JLabel label, JProgressBar pr1,
                         JProgressBar pr2, JLabel label2, JLabel text,
                         JLabel label3, CharacterAction action) {

    Player enemy1 = null;
    if (enemy_num == 0) {
      enemy1 = action.ChooseBoss(label, label2, text, label3, human.getLevel());
    } else {
      enemy1 = action.ChooseEnemy(label, label2, text, label3);
      if (enemy_num == -1) {
        current_location++;
        Random rand = new Random();
        enemy_num = rand.nextInt(2) + human.getLevel() + 1;
        enemy1 = action.ChooseEnemy(label, label2, text, label3);
      }
    }
    pr1.setMaximum(human.getMaxHealth());
    pr2.setMaximum(enemy1.getMaxHealth());
    human.setNewHealth(human.getMaxHealth());
    enemy1.setNewHealth(enemy1.getMaxHealth());
    action.HP(human, pr1);
    action.HP(enemy1, pr2);
    return enemy1;
  }
}
