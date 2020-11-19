//Darian Marvel
//The main game

import java.util.Scanner;
import java.util.Random;
/**
 * A simple game I am working on
 *
 * @author Darian Marvel
 * @version 1.0
 */
public class Game
{
	/**
 	* Main Method for the game
 	*/
	public static void main(String args[]) {
    	System.out.println("Welcome!");
    	System.out.println("In this game, you use your power to summon monsters, to attack your opponent");
    	try{
        	Thread.sleep(500);
    	} catch(InterruptedException ex) {

    	}
    	System.out.println("The power required to summon equals the level of the monster you wish to summon");
    	try{
        	Thread.sleep(500);
    	} catch(InterruptedException ex) {

    	}
    	System.out.println("Attacking costs some power to attack, and you gain some power every turn");
    	try{
        	Thread.sleep(500);
    	} catch(InterruptedException ex) {

    	}
    	System.out.println("Have fun!");
    	try{
        	Thread.sleep(500);
    	} catch(InterruptedException ex) {

    	}
    	System.out.println();
    	System.out.println("Setting up the game..");
    	boolean canCheat=true; //Allows the cheats to be disabled
    	int healMult=2;
    	int APow=0; //Power that the player has
    	int EPow=0; //Power that the enemy has
    	int Limit=10; //The limit of creatures on the field for each player
    	int ALife=1000; //The starting life for the player and the enemy
    	int ELife=1000;
    	int LifeDefense=5000; //The defense making life harder to damage
    	int LifeDefenseA=5000;
    	int LifeDefenseE=5000;
    	int powGain=20; //Power gained per turn by the player and enemy
    	int ACost=-1; //Cost to attack, -1 means it will cost according to level
    	boolean godMode=false;
    	if(ACost==-1) {
        	System.out.println("Attacking will cost power equal to the attacking creature's level");
    	}
    	boolean went; //Whether the player/enemy went or not; used in the while loops
    	boolean went2;
    	int went3;
    	int level;
    	boolean enemLeft; //Used in a for loop to represent if there are any creatures on the enemy's side of the field
    	int choose;
    	int choose2;
    	double damage; //Represents damage done, damage2 and damage3 represent armor and damage
    	int damage2;
    	int damage3;
    	int target; //Allows the enemy to select good targets, by keeping track of which one it chose
    	double target2;
    	boolean done;
    	boolean defensive; //Allows the enemy to play defensively when needed
    	int need; //Power needed to attack
    	int defMult=5; //Helps with targeting
    	boolean needHealing;
    	String in; //The user's command
    	Enemy[] Enem= new Enemy[Limit]; //Creating the enemy and ally objects
    	Ally[] Ally= new Ally[Limit];
    	Scanner input=new Scanner(System.in); //Allowing us to get the commands from the player
    	for (int i=0;i<Limit;i++) { //Instantiating the allies and enemies, and making them not be alive at first
        	Enem[i]=new Enemy(0);
        	Ally[i]=new Ally(0);
        	Enem[i].Die();
        	Ally[i].Die();
    	}
    	System.out.println("Finished setting up the game");
    	boolean gameOver=false; //Whether the game is over or not
    	while(gameOver==false) { //Game loop
        	went=false;
        	while(went==false) { //Player's turn
            	System.out.println("Type \"help\" for command list");
            	System.out.printf("You have %d power\n",APow);
            	System.out.println("What will you do?: ");
            	in=input.next(); //Grab the user's command
            	if(in.equals("cheats9877") && canCheat==true) {
                	System.out.println("The cheats are:");
                	System.out.println("godMode");
                	System.out.println("gimmepow");
                	System.out.println("gimmemonsters");
                	System.out.println("makemewin");
                	System.out.println("makemelose");
            	}
            	if(in.equals("godMode") && canCheat==true) { // cheat, giving life defense and life every turn
                	godMode=true;
                	LifeDefenseA=10000000;
            	}
            	if(in.equals("gimmepow") && canCheat==true) { //cheat, giving the player more power
                	APow+=1000000;
            	}
            	if(in.equals("gimmemonsters") && canCheat==true) { //cheat, giving the player tough monsters
                	Ally[0]=new Ally(100000);
                	Ally[1]=new Ally(100000);
                	Ally[2]=new Ally(100000);
                	Ally[3]=new Ally(100000);
                	Ally[4]=new Ally(100000);
                	Ally[5]=new Ally(100000);
                	Ally[6]=new Ally(100000);
                	Ally[7]=new Ally(100000);
                	Ally[8]=new Ally(100000);
                	Ally[9]=new Ally(100000);
            	}
            	if(in.equals("makemewin") && canCheat==true) { // cheat, setting the enemy's life to 0, so the player wins upon ending his/her turn
                	ELife=0;
            	}
            	if(in.equals("makemelose") && canCheat==true) { //Causes the enemy to summon a tough monster, ends the player's turn, and makes the enemy beat the player
                	ELife=100000;
                	Enem[0]=new Enemy(10000);
                	EPow=10000000;
                	in="end";
            	}
            	if(in.equals("help")) { //Lists commands
                	System.out.println("Commands: help, end, summon, list, and attack");
                	System.out.println("\"end\" - Ends your turn");
                	System.out.println("\"summon\" - Summon a monster, using your power");
                	System.out.println("\"attack\" - Attack with a monster. This uses power equal to the monster's level");
                	System.out.println("\"list\" - Lists your and your opponent's monsters");
                	System.out.println("\"heal\" - Heal your life using power");
            	}
            	if(in.equals("heal")) { //Heal command for defense
                	System.out.println("How much power will you use to heal yourself (int)?");
                	level=input.nextInt();
                	if(level>APow) {
                    	System.out.println("You don't have enough power!");
                	}
                	else if(level<1) {
                    	System.out.println("Now, what would be the point of that?");
                	}
                	else {
                    	ALife+=level*healMult;
                    	APow-=level;
                    	System.out.println("Successfully healed for "+level+" life");
                	}
            	}
            	if(in.equals("summon")) { //The summon command, probably the most important command
                	level=Summon();
                	went2=false;
                	if (level==-1) {
                    	went2=true;
                	}
                	if (level>APow) {
                    	System.out.println("You don't have enough power!");
                    	went2=true;
                	}
                	if(went2==false) {
                    	for(int i=0; i<Limit; i++) { //A for loop, making sure that the player has a slot to summon
                        	if(Ally[i].isAlive()!=true) {
                            	Ally[i]=new Ally(level); //Creates the ally
                            	APow=APow-level; //Takes the cost away from the player's power
                            	went2=true;
                            	System.out.println("Successfully summoned a level "+level+" ally");
                        	}
                        	if(went2==true) {
                            	i=Limit; //Ends the for loop when finished
                        	}
                    	}
                    	if(went2==false) {
                        	System.out.println("Sorry, you can't summon any more creatures!");
                    	}
                	}
            	}
            	if(in.equals("list")) { //Lists creatures on the field, and how much life the player and enemy have
                	for(int i=0; i<Limit; i++) {
                    	System.out.printf("Ally: %d: %d Hp: %f ",i,Ally[i].GetLvl(),Ally[i].GetHp());
                    	System.out.print(Ally[i].isAlive());
                    	System.out.printf("\tEnemy: %d: %d Hp: %f ",i,Enem[i].GetLvl(),Enem[i].GetHp());
                    	System.out.print(Enem[i].isAlive());
                    	System.out.println();
                	}
                	System.out.printf("Your life: %d ",ALife);
                	System.out.printf("\tEnemy life: %d\n",ELife);
            	}
            	if(in.equals("end")) { //Ends the player's turn
                	went=true;
            	}
            	if(in.equals("attack")) { //Allows the player to attack
                	System.out.println("Type \"player\" to attack the enemy player");
                	System.out.println("Attack which enemy?: ");
                	in=input.next();
                	if(in.equals("player")) {
                    	enemLeft=false;
                    	for(int i=0;i<10;i++) {
                        	if(Enem[i].isAlive()==true) {
                            	enemLeft=true;
                        	}
                    	}
                    	if(enemLeft==false) {
                        	System.out.println("With which ally?: ");
                        	choose=input.nextInt();
                        	if(ACost==-1) {
                            	need=Ally[choose].GetLvl();
                        	}
                        	else {
                            	need=ACost;
                        	}
                        	if(Ally[choose].isAlive()==false) {
                            	System.out.println("That ally is not alive!");
                        	}
                        	else if(APow<need) {
                            	System.out.printf("You don't have enough power to attack! (Required: %d)\n",need);
                        	}
                        	else if(Ally[choose].isAlive()==true) {
                            	if(ACost==-1) {
                                	APow-=Ally[choose].GetLvl();
                            	}
                            	else {
                                	APow-=ACost;
                            	}
                            	damage2=Ally[choose].GetAtk();
                            	damage=CalcDamage(damage2,LifeDefenseE);
                            	damage3=(int) damage;
                            	ELife-=damage3;
                            	System.out.printf("You did %d damage to the enemy's life!\n",damage3);
                        	}
                    	}
                    	else {
                        	System.out.println("Sorry, you can't attack directly right now!");
                    	}
                	}
                	else {
                    	choose=Integer.parseInt(in);
                    	if(Enem[choose].isAlive()==false) {
                        	System.out.println("That enemy is not alive!");
                    	}
                    	else {
                        	System.out.println("With which ally?: ");
                        	choose2=input.nextInt();
                        	if(Ally[choose2].isAlive()==false) {
                            	System.out.println("That ally is not alive!");
                        	}
                        	if(ACost==-1) {
                            	need=Ally[choose2].GetLvl();
                        	}
                        	else {
                            	need=ACost;
                        	}
                        	if(APow<need) {
                            	System.out.printf("You don't have enough power to attack! (Required: %d)\n",need);
                        	}
                        	else if(Ally[choose2].isAlive()==true) {
                            	if(ACost==-1) {
                                	APow-=Ally[choose2].GetLvl();
                            	}
                            	else {
                                	APow-=ACost;
                            	}
                            	damage2=Ally[choose2].GetAtk();
                            	damage3=Enem[choose].GetDef();
                            	damage=CalcDamage(damage2,damage3);
                            	Enem[choose].SetHp(Enem[choose].GetHp()-damage);
                            	System.out.printf("Ally %d did %f damage to enemy %d!\n",choose2, damage, choose);
                            	if(Enem[choose].GetHp()<=0) {
                                	Enem[choose].Die();
                                	System.out.println("The Enemy dies!");
                            	}
                        	}
                    	}
                	}
            	}
        	}

        	if(godMode==true) { //The player's god mode
            	ALife=999999;
            	APow=10000000;
        	}
        	if(ELife<=0) { //Win checks
            	System.out.println("You beat the enemy! Congratulations!");
            	gameOver=true;
        	}
        	if(ALife<=0) {
            	System.out.println("Sorry, but you lost! Better luck next time!");
            	gameOver=true;
        	}
        	//System.out.println(EPow); //Debug Statement, to test for correct power

        	/**
         	* Artificial Intelligence
         	* (it's not really that smart)
         	*/
        	if(gameOver==false) {
            	went=false;
        	}
        	went3=0;
        	while(went==false) { //enemy turn, loops a few times just because
            	defensive=false; //Assumes it doesn't have to go defensive...
            	needHealing=false;
            	
            	for(int i=0;i<10;i++) {
                	needHealing=true;
                	if(Enem[i].isAlive()==false) {
                    	needHealing=false;
                    	i=10;
                	}
            	}

            	for(int i=0;i<10;i++) {
                	if(Ally[i].GetLvl()>100 && Ally[i].isAlive()==true) { //...until it realizes that it is necessary
                    	defensive=true;
                	}
            	}
            	
            	if(Enem[4].isAlive()==true) { //Enemy 4 attacks
                	done=false;
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[4].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}

                    	//New targeting statements, should make enemy attacks better
                    	target=-1;
                    	level=10000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetLvl()<level && Ally[i].GetLvl()>5) {
                            	target=i;
                            	level=Ally[i].GetLvl();
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[4].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[4].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if (target>-1 && done==false && CalcDamage( Enem[4].GetAtk() , Ally[target].GetDef() * defMult )>0) {
                        	damage2=Enem[4].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3);
                        	if(damage>0) {
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}
            	
            	if(Enem[3].isAlive()==true) { //Enemy 3 attacks
                	done=false;
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[3].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}

                    	//New targeting statements, should make enemy attacks better
                    	target=-1;
                    	level=10000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetLvl()<level && Ally[i].GetLvl()>3) {
                            	target=i;
                            	level=Ally[i].GetLvl();
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[3].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[3].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if (target>-1 && done==false && CalcDamage( Enem[3].GetAtk() , Ally[target].GetDef() * defMult )>0) {
                        	damage2=Enem[3].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3);
                        	if(damage>0) {
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}
            	
            	if(Enem[2].isAlive()==true) { //Enemy 2 attacks
                	done=false;
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[2].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}

                    	//New targeting statements, should make enemy attacks better
                    	target=-1;
                    	level=10000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetLvl()<level && Ally[i].GetLvl()>1) {
                            	target=i;
                            	level=Ally[i].GetLvl();
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[2].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[2].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if (target>-1 && done==false && CalcDamage( Enem[2].GetAtk() , Ally[target].GetDef() * defMult )>0) {
                        	damage2=Enem[2].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3);
                        	if(damage>0) {
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}

            	if(Enem[1].isAlive()==true) { //Enemy 1 attacks
                	done=false;
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[1].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}

                    	//New targeting statements, should make enemy attacks better
                    	target=-1;
                    	level=10000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetLvl()<level) {
                            	target=i;
                            	level=Ally[i].GetLvl();
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[1].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[1].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if (target>-1 && done==false && CalcDamage( Enem[1].GetAtk() , Ally[target].GetDef() * defMult )>0) {
                        	damage2=Enem[1].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3);
                        	if(damage>0) {
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}

            	if(Enem[9].isAlive()==true) { //allows enemy 9 to attack
                	done=false;
                	if(Enem[0].isAlive()==true && Enem[0].GetLvl()>Enem[9].GetLvl())
                	{
                    	done=true;
                    	if(went3>0) {
                        	done=false;
                    	}
                	}
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[9].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                    	target=-1;
                    	target2=100000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetHp()<target2) {
                            	target=i;
                            	target2=Ally[i].GetHp();
                        	}
                    	}

                    	if(target>-1 && CalcDamage(Enem[0].GetAtk(),Ally[target].GetDef())==0) { //New targeting statements, should make enemy attacks better
                        	target=-1;
                        	level=10000;
                        	for(int i=0;i<10;i++) {
                            	if(Ally[i].isAlive()==true && Ally[i].GetHp()<level) {
                                	target=i;
                                	level=Ally[i].GetLvl();
                            	}
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[9].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[9].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if(done==false && target!=-1) {
                        	damage2=Enem[9].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3 * defMult );
                        	if(damage>0) {
                            	damage=CalcDamage(damage2,damage3);
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                        	else {
                            	done=true;
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}
            	
            	if(Enem[0].isAlive()==false) { //Summons the non-defensive ally 0
                	level=EPow-10;
                	if(level>=90) {
                    	Enem[0]=new Enemy(level);
                    	System.out.println("Enemy Summons a level " + level + " monster!");
                    	EPow-=level;
                	}
            	}
            	
            	/*

            	if(Enem[0].isAlive()==false && defensive==false) { //Summons the non-defensive ally 0
                	level=EPow-10;
                	if(level>=90) {
                    	Enem[0]=new Enemy(level);
                    	System.out.println("Enemy Summons a level " + level + " monster!");
                    	EPow-=level;
                	}
            	}
            	
            	
            	
            	else if(Enem[0].isAlive()==false) { //Goes defensive, by making it cheap, but it can block hits
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[0]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	
            	*/
           	
            	if(Enem[1].isAlive()==false) { //Enemies 1-8 Are low levels, and block hits from the player, making it harder to win
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[1]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	if(Enem[2].isAlive()==false) {
                	if(EPow>=3) {
                    	EPow=EPow-3;
                    	Enem[2]=new Enemy(3);
                    	System.out.println("Enemy Summons a level 3 monster!");
                	}
            	}
            	if(Enem[3].isAlive()==false) {
                	if(EPow>=5) {
                    	EPow=EPow-5;
                    	Enem[3]=new Enemy(5);
                    	System.out.println("Enemy Summons a level 5 monster!");
                	}
            	}
            	if(Enem[4].isAlive()==false) {
                	if(EPow>=8) {
                    	EPow=EPow-8;
                    	Enem[4]=new Enemy(8);
                    	System.out.println("Enemy Summons a level 8 monster!");
                	}
            	}
            	if(Enem[5].isAlive()==false) {
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[5]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	if(Enem[6].isAlive()==false) {
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[6]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	if(Enem[7].isAlive()==false) {
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[7]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	if(Enem[8].isAlive()==false) {
                	if(EPow>=1) {
                    	EPow=EPow-1;
                    	Enem[8]=new Enemy(1);
                    	System.out.println("Enemy Summons a level 1 monster!");
                	}
            	}
            	
            	if(Enem[0].isAlive()==true) { //Enemy 0 attacks
                	done=false;
                	while(done==false) {
                    	if(ACost==-1) {
                        	need=Enem[0].GetLvl();
                    	}
                    	else {
                        	need=ACost;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                    	target=-1;
                    	target2=100000;
                    	for(int i=0;i<10;i++) {
                        	if(Ally[i].isAlive()==true && Ally[i].GetHp()<target2) {
                            	target=i;
                            	target2=Ally[i].GetHp();
                        	}
                    	}

                    	if(target>-1 && CalcDamage(Enem[0].GetAtk(),Ally[target].GetDef())==0) { //New targeting statements, should make enemy attacks better
                        	target=-1;
                        	level=10000;
                        	for(int i=0;i<10;i++) {
                            	if(Ally[i].isAlive()==true && Ally[i].GetHp()<level) {
                                	target=i;
                                	level=Ally[i].GetLvl();
                            	}
                        	}
                    	}

                    	if(target==-1 && done==false && CalcDamage(Enem[0].GetAtk(),LifeDefenseA)>0) {
                        	damage2=Enem[0].GetAtk();
                        	damage3=LifeDefense;
                        	damage=CalcDamage(damage2,damage3);
                        	damage3=(int) damage;
                        	ALife-=damage3;
                        	System.out.printf("The enemy did %d damage to your life!\n",damage3);
                        	EPow-=need;
                    	}
                    	else if (target>-1 && done==false && CalcDamage(Enem[0].GetAtk(),Ally[target].GetDef() * defMult )>0) {
                        	damage2=Enem[0].GetAtk();
                        	damage3=Ally[target].GetDef();
                        	damage=CalcDamage(damage2,damage3);
                        	if(damage>0) {
                            	Ally[target].SetHp(Ally[target].GetHp()-damage);
                            	System.out.printf("The enemy did %f damage to ally %d!\n",damage,target);
                            	EPow-=need;
                            	if(Ally[target].GetHp()<=0) {
                                	System.out.printf("Ally %d died!\n",target);
                                	Ally[target].Die();
                            	}
                        	}
                    	}
                    	else{
                        	done=true;
                    	}
                    	if(EPow<need) {
                        	done=true;
                    	}
                	}
            	}

            	if(Enem[9].isAlive()==false && defensive==true) { //Helps ally 9 break through the player's monsters
                	if(EPow>=500) {
                    	EPow=EPow-500;
                    	Enem[9]=new Enemy(500);
                    	System.out.println("Enemy Summons a level 500 monster!");
                	}
            	}

            	if(Enem[9].isAlive()==false) { //Summons the normal enemy 9
                	target=0;
                	for(int i=0;i<10;i++) {
                    	if(Ally[i].isAlive()==true && Ally[i].GetLvl()>target) {
                        	target=Ally[i].GetLvl();
                    	}
                	}
                	if(EPow>target && target>0) {
                    	Enem[9]=new Enemy(target);
                    	EPow-=target;
                    	System.out.println("Enemy summons a level "+target+" monster!");
                	}
            	}

            	went3++;
            	
            	if(needHealing==true) {
                	System.out.println("The enemy healed himself for "+EPow*healMult+" life!");
                	ELife+=EPow*healMult;
                	EPow=0;
            	}
            	
            	if(went3>=5) {
                	went=true; //Ends the enemy's turn
            	}
        	}
        	//System.out.println(EPow);
        	//Power Gaining at the end of every turn
        	APow+=powGain; //Adding power, making sure it isn't over the limit
        	EPow+=powGain;
        	if(APow>=10000) {
            	APow=10000;
        	}
        	if(EPow>=10000) {
            	EPow=10000;
        	}
        	if(godMode==true) {
            	ALife=99999;
        	}
        	if(ELife<=0) { //Win checks for the enemy and the player
            	System.out.println("You beat the enemy! Congratulations!");
            	gameOver=true;
        	}
        	if(ALife<=0) {
            	System.out.println("Sorry, but you lost! Better luck next time!");
            	gameOver=true;
        	}
        	//System.out.println(EPow);
        	
        	if(godMode==true) { //The player's god mode
            	ALife=999999;
            	APow=10000000;
        	}
    	}
	}

	/**
 	* Allows the player to summon, by getting a valid level of what they want to summon
 	*/
	public static int Summon() {
    	System.out.println("So, you want to summon.");
    	System.out.println("Type \"back\" to go back");
    	System.out.println("Give me the level of the creature you wish to summon (int): ");
    	boolean done=false;
    	String input;
    	int level=-1;
    	Scanner in = new Scanner(System.in);
    	while(done==false) {
        	input=in.next();
        	if(input.equals("back")) {
            	return -1;
        	}
        	level= Integer.parseInt(input);
        	if(level>0) {
            	done=true;
        	}
        	else {
            	System.out.println("Invalid level");
        	}
    	}
    	return level;
	}

	/**
 	* Calculates the damage done
 	* @param damage attack stat of the attacker
 	* @param armor defense stat of the person being attacked
 	* @return The damage done as a double
 	* This damage system relies on the relation of the attack power to the defense of the person being attacked.
 	* If the attack power is half of the defense, the damage is halved.
 	* If the attack power is one tenth of the defense, the attack is blocked, and does 0 damage.
 	*/
	public static double CalcDamage(int damage, int armor) {
    	double a=damage; //A is the damage, b is the armor of the person being attacked
    	double b=armor;
    	double c=a/b; //Finds the multiplier
    	double finalDamage;
    	if(a>b) { //Reduces damage multiplier if the attack is greater than the defense
        	c=c-1;
        	c=c/3.0;
        	c=c+1;
    	}
    	if(c<=0.1) { //if the attack does very low damage, it does 0
        	return 0;
    	}
    	else {
        	finalDamage=a*c; //Uses the multiplier to calculate and return the damage done
        	return finalDamage;
    	}
	}
}