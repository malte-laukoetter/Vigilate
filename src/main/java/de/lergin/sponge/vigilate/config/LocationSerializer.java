package de.lergin.sponge.vigilate.config;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class LocationSerializer implements TypeSerializer<Location> {
    @Override
    public Location deserialize(TypeToken<?> typeToken, ConfigurationNode value) throws ObjectMappingException {
        String name = value.getNode("world").getValue(TypeToken.of(String.class));
        Double x = value.getNode("x").getValue(TypeToken.of(Double.class));
        Double y = value.getNode("y").getValue(TypeToken.of(Double.class));
        Double z = value.getNode("z").getValue(TypeToken.of(Double.class));

        Optional<World> optional = Sponge.getServer().getWorld(name);

        if (!optional.isPresent()) {
            throw new ObjectMappingException("Unknown world");
        }

        World world = optional.get();
        return world.getLocation(x, y, z);
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Location loc, ConfigurationNode value) throws ObjectMappingException {
        if(loc.getExtent() instanceof World){
            value.getNode("world").setValue(((World) loc.getExtent()).getName());
        }else{
            throw new ObjectMappingException("No world Extent");
        }

        value.getNode("x").setValue(loc.getX());
        value.getNode("y").setValue(loc.getY());
        value.getNode("z").setValue(loc.getZ());
    }
}