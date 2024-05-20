/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
package edu.mephi;

import edu.mephi.enemys.BarakaFabric;
import edu.mephi.enemys.LiuKangFabric;
import edu.mephi.enemys.ShaoKahnFabric;
import edu.mephi.enemys.SonyaBladeFabric;
import edu.mephi.enemys.SubZeroFabric;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Мария
 */
public class EnemyFabric {

  public Player create(int i, int j) {
    EnemyFabricInterface fabric = null;

    switch (i) {
    case 0:
      fabric = new BarakaFabric();
      break;
    case 1:
      fabric = new SubZeroFabric();
      break;
    case 2:
      fabric = new LiuKangFabric();
      break;
    case 3:
      fabric = new SonyaBladeFabric();
      break;
    case 4:
      fabric = new ShaoKahnFabric();
      break;
    }
    Player enemy = fabric.create(j);
    return enemy;
  }
}
