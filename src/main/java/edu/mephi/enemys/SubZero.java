/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license Click
 * nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this
 * template
 */
package edu.mephi.enemys;

import edu.mephi.Player;

/**
 *
 * @author Мария
 */
public class SubZero extends Player {

  public SubZero(int level, int health, int damage, int attack) {
    super(level, health, damage, attack);
  }

  @Override
  public String getName() {
    return "Sub-Zero";
  }
}
