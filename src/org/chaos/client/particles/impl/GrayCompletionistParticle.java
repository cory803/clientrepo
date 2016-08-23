package org.chaos.client.particles.impl;

import org.chaos.client.particles.Particle;
import org.chaos.client.particles.Position;

public final class GrayCompletionistParticle extends Particle {

	public GrayCompletionistParticle() {
		this.Z(new Position(0, -3, 0));
		this.getImage(new Position(0, -3, 0));
		this.getImage(19);
		this.setRGB(0x7F7E7E);
		this.I(1);
		this.Z(1.5F);
		this.getImage(0.0F);
		this.I(0.075F);
		this.K();
		this.B(0);
	}

}