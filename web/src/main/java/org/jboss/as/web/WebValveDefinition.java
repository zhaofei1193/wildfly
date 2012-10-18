package org.jboss.as.web;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PropertiesAttributeDefinition;
import org.jboss.as.controller.ReloadRequiredWriteAttributeHandler;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.operations.validation.StringLengthValidator;
import org.jboss.as.controller.registry.AttributeAccess;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.dmr.ModelType;

public class WebValveDefinition extends SimpleResourceDefinition {
    protected static final WebValveDefinition INSTANCE = new WebValveDefinition();

    protected static final SimpleAttributeDefinition MODULE = new SimpleAttributeDefinitionBuilder(Constants.MODULE,
            ModelType.STRING).setXmlName(Constants.MODULE).setAllowNull(true)
            .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES).setValidator(new StringLengthValidator(1)).build();

    protected static final SimpleAttributeDefinition CLASS_NAME = new SimpleAttributeDefinitionBuilder(Constants.CLASS_NAME,
            ModelType.STRING).setXmlName(Constants.CLASS_NAME).setAllowNull(false)
            .setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES).setValidator(new StringLengthValidator(1)).build();

    protected static final SimpleAttributeDefinition[] ATTRIBUTES = { MODULE, CLASS_NAME };

    protected static final PropertiesAttributeDefinition PARAMS = new PropertiesAttributeDefinition(Constants.PARAM,
            Constants.PARAM, true);

    private static final SimpleAttributeDefinition PARAM_NAME = new SimpleAttributeDefinitionBuilder(Constants.PARAM_NAME,
            ModelType.STRING, true).setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
            .setValidator(new StringLengthValidator(1, true)).build();

    private static final SimpleAttributeDefinition PARAM_VALUE = new SimpleAttributeDefinitionBuilder(Constants.PARAM_VALUE,
            ModelType.STRING, true).setFlags(AttributeAccess.Flag.RESTART_ALL_SERVICES)
            .setValidator(new StringLengthValidator(1, true)).build();

    private WebValveDefinition() {
        super(WebExtension.VALVE_PATH, WebExtension.getResourceDescriptionResolver(Constants.VALVE), WebValveAdd.INSTANCE,
                WebValveRemove.INSTANCE);
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration valves) {
        for (AttributeDefinition def : ATTRIBUTES) {
            valves.registerReadWriteAttribute(def, null, new ReloadRequiredWriteAttributeHandler(def));
        }
        valves.registerReadWriteAttribute(PARAMS, null, new ReloadRequiredWriteAttributeHandler(PARAMS));
    }
}
