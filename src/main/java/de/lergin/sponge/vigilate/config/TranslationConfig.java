package de.lergin.sponge.vigilate.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import static org.spongepowered.api.text.TextTemplate.arg;

@ConfigSerializable
public class TranslationConfig {
    @Setting(value = "camera_view", comment = "The Text shown when viewing a camera")
    public TextTemplate CAMERA_VIEW = TextTemplate.of(
            "Viewing Camera: ", arg("camera.name").build()
    );

    @Setting(value = "camera_endview", comment = "The text shown when ending the view of a camera")
    public TextTemplate CAMERA_ENDVIEW = TextTemplate.of(
            "Ended Camera view!"
    );

    @Setting(value = "camera_view_title", comment = "Title text shown while viewing the camera")
    public TextTemplate CAMERA_VIEW_TITLE = TextTemplate.of(
            "Click to go back!"
    );

    @Setting(value = "unknown_camera_id", comment = "Error when a command is called with an unknown camera")
    public TextTemplate UNKNOWN_CAMERA_ID = TextTemplate.of(
            "Unknown Camera Id (", arg("camera.id"), ")"
    );

    @Setting(value = "camera_created", comment = "The Text shown after the creation of a camera")
    public TextTemplate CAMERA_CREATED = TextTemplate.of(
            "Successful created camera ", arg("camera.id").style(TextStyles.ITALIC).color(TextColors.GREEN), "!"
    );

    @Setting(value = "camera_created_has_permissions", comment = "The additional Text shown after the creation of a camera if the creator can use the camera, when clicked it will teleport player to view the cam")
    public TextTemplate CAMERA_CREATED_HAS_PERMISSIONS = TextTemplate.of(
            TextStyles.ITALIC, "View Camera"
    );

    @Setting(value = "camera_deleted", comment = "The text shown after the deletion of a camera")
    public TextTemplate CAMERA_DELTED = TextTemplate.of(
            "Successful deleted camera ", arg("camera.id").style(TextStyles.ITALIC).color(TextColors.GREEN), "!"
    );

    @Setting(value = "camera_info", comment = "The info text about camera")
    public TextTemplate CAMERA_INFO = TextTemplate.of(
            "Camera Info - ", arg("camera.name").build(), Text.NEW_LINE,
            "Id: ", arg("camera.id").build(), Text.NEW_LINE,
            "Permission: ", arg("camera.permission").build(), Text.NEW_LINE,
            "World: ", arg("camera.world").build(), Text.NEW_LINE,
            "Position: ", arg("camera.x").build(),
            "/", arg("camera.y").build(),
            "/", arg("camera.z").build()
    );

    @Setting(value = "camera_list_title", comment = "The title of the camera list")
    public Text CAMERA_LIST_TITLE = Text.of("Cameras");

    @Setting(value = "camera_list_item", comment = "The text of an item of the camera list")
    public TextTemplate CAMERA_LIST_ITEM = TextTemplate.of(
            arg("camera.name")
    );

    @Setting(value = "camera_list_item_hover", comment = "The hovertext of an item of the camera list")
    public TextTemplate CAMERA_LIST_ITEM_HOVER = TextTemplate.of(
            arg("camera.x"), "/",
            arg("camera.y"), "/",
            arg("camera.z")
    );

    @Setting(value = "camera_view_no_player", comment = "The hovertext of an item of the camera list")
    public Text CAMERA_VIEW_NO_PLAYER = Text.of("Only Players can view Cameras");

    @Setting(value = "reloaded", comment = "The text shown if the plugin has been reloaded")
    public Text RELOADED = Text.of(TextColors.RED, "Reloaded Vigilate");

    @Setting(value = "reload failed", comment = "The text shown if the reload has failed")
    public Text RELOAD_FAILED = Text.of(TextColors.RED, "Couldn't reload Vigilate!");

    /*
     * Command Descriptions
     */

    @Setting(value = "command_create", comment = "Description of the create command")
    public Text COMMAND_CREATE = Text.of("Creates a new Camera");

    @Setting(value = "command_delete", comment = "Description of the delete command")
    public Text COMMAND_DELETE = Text.of("Deletes a Camera");

    @Setting(value = "command_view", comment = "Description of the view command")
    public Text COMMAND_VIEW = Text.of("Views a Camera");

    @Setting(value = "command_info", comment = "Description of the info command")
    public Text COMMAND_INFO = Text.of("Shows Infos about a Camera");

    @Setting(value = "command_list", comment = "Description of the list command")
    public Text COMMAND_LIST = Text.of("List all Cameras");

    @Setting(value = "command_reload", comment = "Description of the reload command")
    public Text COMMAND_RELOAD = Text.of("Reloads the Configuration");

    public TranslationConfig(){

    }
}
