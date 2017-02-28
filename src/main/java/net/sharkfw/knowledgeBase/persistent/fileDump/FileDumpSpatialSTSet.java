package net.sharkfw.knowledgeBase.persistent.fileDump;

import net.sharkfw.knowledgeBase.FragmentationParameter;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.SpatialSTSet;
import net.sharkfw.knowledgeBase.SpatialSemanticTag;
import net.sharkfw.knowledgeBase.geom.SharkGeometry;

import java.util.Enumeration;

/**
 * Created by j4rvis on 2/28/17.
 */
public class FileDumpSpatialSTSet extends FileDumpSTSet implements SpatialSTSet {
    private final SpatialSTSet spatialSTSet;

    public FileDumpSpatialSTSet(FileDumpSharkKB kb, SpatialSTSet set) {
        super(kb, set);
        spatialSTSet = set;
    }

    @Override
    public SpatialSTSet contextualize(SpatialSTSet context, FragmentationParameter fp) throws SharkKBException {
        SpatialSTSet contextualize = this.spatialSTSet.contextualize(context, fp);
        this.kb.persist();
        return new FileDumpSpatialSTSet(this.kb, contextualize);
    }

    @Override
    public double getDistance(SpatialSemanticTag gc1, SpatialSemanticTag gc2) {
        return this.spatialSTSet.getDistance(gc1, gc2);
    }

    @Override
    public boolean isInRange(SpatialSemanticTag gc1, SpatialSemanticTag gc2, double radius) {
        return this.spatialSTSet.isInRange(gc1, gc2, radius);
    }

    @Override
    public SpatialSemanticTag createSpatialSemanticTag(String name, String[] sis, SharkGeometry geometry) throws SharkKBException {
        SpatialSemanticTag spatialSemanticTag = this.spatialSTSet.createSpatialSemanticTag(name, sis, geometry);
        this.kb.persist();
        return new FileDumpSpatialSemanticTag(this.kb, spatialSemanticTag);
    }

    @Override
    public SpatialSemanticTag createSpatialSemanticTag(String name, String[] sis, SharkGeometry[] geometries) throws SharkKBException {
        SpatialSemanticTag spatialSemanticTag = this.spatialSTSet.createSpatialSemanticTag(name, sis, geometries);
        this.kb.persist();
        return new FileDumpSpatialSemanticTag(this.kb, spatialSemanticTag);

    }

    @Override
    public SpatialSemanticTag getSpatialSemanticTag(String[] sis) throws SharkKBException {
        SpatialSemanticTag spatialSemanticTag = this.spatialSTSet.getSpatialSemanticTag(sis);
        this.kb.persist();
        return new FileDumpSpatialSemanticTag(this.kb, spatialSemanticTag);
    }

    @Override
    public SpatialSemanticTag getSpatialSemanticTag(String si) throws SharkKBException {
        SpatialSemanticTag spatialSemanticTag = this.spatialSTSet.getSpatialSemanticTag(si);
        this.kb.persist();
        return new FileDumpSpatialSemanticTag(this.kb, spatialSemanticTag);
    }

    @Override
    public Enumeration<SpatialSemanticTag> spatialTags() throws SharkKBException {
        return this.spatialSTSet.spatialTags();
    }

}
