package com.example.customoverlays;

import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import androidx.fragment.app.Fragment;
public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
            // Create anchor and add custom overlay
            createOverlay(hitResult.createAnchor());
        });
    }

    private void createOverlay(Anchor anchor) {
        // Build a ViewRenderable (for text overlay)
        ViewRenderable.builder()
                .setView(this, R.layout.custom_overlay)
                .build()
                .thenAccept(viewRenderable -> addNodeToScene(anchor, viewRenderable));
    }

    private void addNodeToScene(Anchor anchor, ViewRenderable renderable) {
        // Create anchor node
        AnchorNode anchorNode = new AnchorNode(anchor);
        anchorNode.setParent(arFragment.getArSceneView().getScene());

        // Create a transformable node for placing the overlay
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setParent(anchorNode);
        node.setRenderable(renderable);
        node.select();
    }
}
