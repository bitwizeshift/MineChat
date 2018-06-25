package com.punchingwood.minechat;

public enum CommandName
{
    SAY("say"),
    PRIVATE_MESSAGE("pm"),
    MINECHAT("minechat")
    ;
    
    private final String name;

    private CommandName( final String name )
    {
        this.name = name;
    }
    
    public String getName() { return this.name; }
    
    @Override
    public String toString() { return this.name; }
}
