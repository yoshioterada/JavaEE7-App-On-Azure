/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azuresql.cdis;
import com.microsoft.yoshio3.azuresql.entities.Employee;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author tyoshio2002
 */
@Named(value = "selectedRow")
@ConversationScoped
public class SelectedRow implements Serializable{
    @Inject
    Conversation conversation;
    
    private Employee selectedEmployee;
    
    /**
     * @return the selectedEmployee
     */
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    /**
     * @param selectedEmployee the selectedEmployee to set
     */
    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public void startConversation(){
        if(conversation.isTransient()){
            conversation.begin();
        }        
    }
    
    
    public void finishConversation(){
        if(conversation.isTransient()){
            conversation.end();
        }        
    }
    
    
}
