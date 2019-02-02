package com.example.fujis.invadergame;

public class Collision {
    private Player player;
    private Enemy enemy;
//    used by preventing player from getting damaged a lot in a short period
    private long t1=0;
    private long t2=0;
//    when payer or enemy get damaged, stop getting damage intermittently
    private boolean playerGettingDamagedAFewSecondsAgo,enemyGettingDamagedAFewSecondsAgo;

    public Collision(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }
//    getter

    public boolean isPlayerGettingDamagedAFewSecondsAgo() {
        return playerGettingDamagedAFewSecondsAgo;
    }

    public boolean isEnemyGettingDamagedAFewSecondsAgo() {
        return enemyGettingDamagedAFewSecondsAgo;
    }

    //    call private methods in this inner class
    public void collision(){
        playerGetDamagedOrNot();
        enemyGetDamagedOrNot();
    }
//    check if bullets from enemy hit player or not
    private void playerGetDamagedOrNot(){
        if(System.currentTimeMillis()-t1>800) {
            playerGettingDamagedAFewSecondsAgo=false;
            for (int i = 0; i < enemy.getListOfPositionOfBulletFromEnemy().size(); i = i + 2) {
//            check if bullet's X position is within
                if (player.getAlive()&&player.getLeft() < enemy.getListOfPositionOfBulletFromEnemy().get(i) && enemy.getListOfPositionOfBulletFromEnemy().get(i) < player.getLeft() + player.getWIDTH_OF_PLAYER()) {
                    if (player.getTop() < enemy.getListOfPositionOfBulletFromEnemy().get(i + 1) && enemy.getListOfPositionOfBulletFromEnemy().get(i + 1) < player.getTop() + player.getHEIGHT_OF_PLAYER()) {
                        player.getDamage(1);
                        enemy.getListOfPositionOfBulletFromEnemy().remove(i);
                        enemy.getListOfPositionOfBulletFromEnemy().remove(i);
                        t1=System.currentTimeMillis();
                        playerGettingDamagedAFewSecondsAgo=true;
                    }
                }
            }
        }
    }
//    check if bullets from player hit enemy or not
    private void enemyGetDamagedOrNot(){
        if(System.currentTimeMillis()-t2>800) {
            enemyGettingDamagedAFewSecondsAgo=false;
            for (int i = 0; i < player.getListOfPositionOfBulletFromPlayer().size(); i = i + 2) {
                if (enemy.getAlive()&&enemy.getLeft() < player.getListOfPositionOfBulletFromPlayer().get(i) && player.getListOfPositionOfBulletFromPlayer().get(i) < enemy.getLeft() + enemy.getWIDTH_OF_ENEMY()) {
                    if (enemy.getTop() < player.getListOfPositionOfBulletFromPlayer().get(i + 1) && player.getListOfPositionOfBulletFromPlayer().get(i + 1) < enemy.getTop() + enemy.getHEIGHT_OF_ENEMY()) {
                        enemy.getDamage(1);
                        player.getListOfPositionOfBulletFromPlayer().remove(i);
                        player.getListOfPositionOfBulletFromPlayer().remove(i);
                        t2=System.currentTimeMillis();
                        enemyGettingDamagedAFewSecondsAgo=true;
                    }
                }
            }
        }
    }
}
