package com.Darian.RPG;
/**
 * This interface sets the requirements for mods
 */
public interface Mod
{
    public void command(String m, readThread from);
    public void init();
    public void use(Character user, Character target, Ability ability);
    public void tick();
}
