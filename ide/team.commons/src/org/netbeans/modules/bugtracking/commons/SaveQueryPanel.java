/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * SavePanel.java
 *
 * Created on Mar 23, 2010, 4:01:47 PM
 */

package org.netbeans.modules.bugtracking.commons;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

/**
 *
 * @author tomas
 */
public class SaveQueryPanel extends javax.swing.JPanel implements DocumentListener {
    private static JButton ok;
    private DialogDescriptor descriptor;
    private QueryNameValidator validator;
    private static final Color ERROR_COLOR;
    static {
        Color c = UIManager.getColor("nb.errorForeground"); //NOI18N
        if (c == null) {
            c = new Color(153, 0, 0);
        }
        ERROR_COLOR = c;
    }

    /** Creates new form SavePanel */
    private SaveQueryPanel() {
        initComponents();
        queryNameTextField.getDocument().addDocumentListener(this);

        saveErrorLabel.setForeground(ERROR_COLOR);
        Icon icon = ImageUtilities.loadIcon("org/netbeans/modules/bugtracking/ui/resources/error.gif"); //NOI18N
        saveErrorLabel.setIcon( icon );
        saveErrorLabel.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(SaveQueryPanel.class, "SaveQueryPanel.jLabel3.text")); // NOI18N

        saveErrorLabel.setText(org.openide.util.NbBundle.getMessage(SaveQueryPanel.class, "SaveQueryPanel.saveErrorLabel.text_1")); // NOI18N

        queryNameTextField.setColumns(18);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveErrorLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(queryNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(queryNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveErrorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    final javax.swing.JTextField queryNameTextField = new javax.swing.JTextField();
    final javax.swing.JLabel saveErrorLabel = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables

    @Override
    public void insertUpdate(DocumentEvent e) {
        validatePanel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        validatePanel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        validatePanel();
    }

    private void validatePanel() {
        String name = queryNameTextField.getText();
        String errText = validator.isValid(name);
        boolean valid = !name.equals("") && errText == null;
        ok.setEnabled(valid);
        if(errText != null) {
            saveErrorLabel.setText(errText);
            saveErrorLabel.setVisible(true);
        } else {
            saveErrorLabel.setText("");
            saveErrorLabel.setVisible(false);
        }
    }

    public static String show(QueryNameValidator validator, HelpCtx helpCtx) {
        SaveQueryPanel panel = new SaveQueryPanel();
        panel.validator = validator;
        if (panel.descriptor == null) {
            ok = new JButton(NbBundle.getMessage(SaveQueryPanel.class, "LBL_Save")); // NOI18N
            ok.getAccessibleContext().setAccessibleDescription(ok.getText());
            JButton cancel = new JButton(NbBundle.getMessage(SaveQueryPanel.class, "LBL_Cancel")); // NOI18N
            cancel.getAccessibleContext().setAccessibleDescription(cancel.getText());
            panel.descriptor = new DialogDescriptor(
                    panel,
                    NbBundle.getMessage(SaveQueryPanel.class, "LBL_SaveQuery"), // NOI18N
                    true,
                    new Object[]{ok, cancel},
                    ok,
                    DialogDescriptor.DEFAULT_ALIGN,
                    helpCtx,
                    null);
        }
        panel.validatePanel();
        if(DialogDisplayer.getDefault().notify(panel.descriptor) == ok) {
            return panel.queryNameTextField.getText();
        } else {
            return null;
        }
    }

    public abstract static class QueryNameValidator {
        public abstract String isValid(String name);
    }
}
