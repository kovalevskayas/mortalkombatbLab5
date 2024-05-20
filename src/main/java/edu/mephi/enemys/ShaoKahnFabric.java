/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
package edu.mephi.enemys;
import edu.mephi.EnemyFabricInterface;
import edu.mephi.Player;

/**
 *
 * @author Мария
 */
public class ShaoKahnFabric implements EnemyFabricInterface {

  @Override
  public Player create(int i) {
    Player enemy;
    if (i == 0) {
      enemy = new ShaoKahn(1, 100, 30, 1);
    } else {
      enemy = new ShaoKahn(3, 145, 44, 1);
    }
    return enemy;
  }
}
