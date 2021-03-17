package com.company.Parsing;

import com.company.DBExceptions.*;

import java.util.ArrayList;

public class DropCMD extends Parser implements DBCommand{

    private final ArrayList<String> command;
    private int index;
    private StorageType type;
    private String structureName;
    private String databaseName;
    private String tableName;

    public DropCMD(ArrayList<String> command, int index) throws DBException {
        this.command = command;
        this.index = index;
        if(command != null) {
            if (!parseDrop()) {
                throw new CommandException(
                        command.get(index), index, "database or table name");
            }
        }else{
            throw new EmptyData("DROP command");
        }
    }

    private boolean parseDrop() throws DBException{
        index++;
        String nextToken = command.get(index);
        switch(nextToken){
            case ("database"):
                type = StorageType.DATABASE;
                databaseName = parseDatabaseName(command, index);
                //increase index to be pointing to the ; after databaseName
                index += 2;
                break;
            case ("table"):
                type = StorageType.TABLE;
                tableName = parseTableName(command, index);
                index += 2;
                //now we have a problem with index if there is an attribute list...
                break;
            default:
                return false;
        }
        return true;
    }

    public StorageType getType(){
        return type;
    }

    public String getTableName() throws EmptyData {
        if(tableName!=null) {
            return tableName;
        }
        throw new EmptyData("table name");
    }

    public String getDatabaseName() throws EmptyData {
        if(databaseName!=null) {
            return databaseName;
        }
        throw new EmptyData("database name");
    }

    public String getStructureName() throws EmptyData {
        if(structureName!=null){
            return structureName;
        }
        else{
            throw new EmptyData("structure name");
        }
    }

    public void execute(){}

    public int getIndex(){
        return index;
    }
}
