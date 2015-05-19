package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class AirCommanderContactListener implements ContactListener {
	
	public static final String TAG = Level.class.getName();
	
	
	public void beginContact(Contact contact) {
	
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		// bullet hits player block code
		
		String fixtureALong = fixtureA.getUserData().toString();
		String fixtureBLong = fixtureB.getUserData().toString();
		
		String fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		String fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		if (fixtureAShort.equals("bullet") &&
			fixtureBShort.equals("player")) {
				
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
				
		}
	
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		if (fixtureAShort.equals("player") &&
			fixtureBShort.equals("bullet")) {
				
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
			
		}
		
		// plane hits player block code
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 5));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
	
		if (fixtureAShort.equals("plane") &&
		    fixtureBShort.equals("player")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
			
		}
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
		
		if (fixtureAShort.equals("player") &&
			fixtureBShort.equals("plane")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
		
		}

	}
	
	public void endContact(Contact contact) {
		
	}
	
	public void preSolve(Contact contact, Manifold manifold) {
		
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		String fixtureALong = fixtureA.getUserData().toString();
		String fixtureBLong = fixtureB.getUserData().toString();
				
		String fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		String fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		// ignore bullet on bullet collisions
		
		if (fixtureAShort.equals("bullet") &&
		    fixtureBShort.equals("bullet")) {
		
			contact.setEnabled(false);

		}
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 7));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
		
		// bullet 1 hits plane 1
		
		if (fixtureAShort.equals("bullet1") &&
		    fixtureBShort.equals("plane")) {
				
			fixtureA.setUserData("delete");
			fixtureB.setUserData("hit");
			
			contact.setEnabled(false);
				
		}
		
		// bullet 1 hits plane 2
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 5));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 7));
		
		if (fixtureAShort.equals("plane") &&
			fixtureBShort.equals("bullet1")) {
			
			fixtureA.setUserData("hit");
			fixtureB.setUserData("delete");
			
			contact.setEnabled(false);
		
		}	

		// bullet 1 hits tank
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 7));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 4));
		
		if (fixtureAShort.equals("bullet1") &&
			fixtureBShort.equals("tank")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("hit");
				
			contact.setEnabled(false);
			
		}	
		
		// tank hits bullet 1
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 4));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 7));
		
		if (fixtureAShort.equals("tank") &&
			fixtureBShort.equals("bullet1")) {
			
			fixtureA.setUserData("hit");
			fixtureB.setUserData("delete");
				
			contact.setEnabled(false);
			
		}		
		
		// ignore plane hits tank
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 5));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 4));
		
		if (fixtureAShort.equals("plane") &&
			fixtureBShort.equals("tank")) {
				
			contact.setEnabled(false);
			
		}	
		
		// ignore tank hits plane
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 4));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
		
		if (fixtureAShort.equals("tank") &&
			fixtureBShort.equals("plane")) {
				
			contact.setEnabled(false);
			
		}	
		
		// ignore plane hits bullet2 
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 5));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 7));
		
		if (fixtureAShort.equals("plane") &&
			fixtureBShort.equals("bullet2")) {
				
			contact.setEnabled(false);
			
		}		
		
		// ignore bullet2 hits plane 
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 7));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
		
		if (fixtureAShort.equals("bullet2") &&
			fixtureBShort.equals("plane")) {
				
			contact.setEnabled(false);
			
		}		
		
		// ignore tank hits bullet2 
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 4));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 7));
		
		if (fixtureAShort.equals("tank") &&
			fixtureBShort.equals("bullet2")) {
				
			contact.setEnabled(false);
			
		}		
		
		// ignore bullet2 hits tank
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 7));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 4));
		
		if (fixtureAShort.equals("bullet2") &&
			fixtureBShort.equals("tank")) {
			
			contact.setEnabled(false);
			
		}		
		
		
		// ignore all collisions with turret
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		if (fixtureAShort.equals("turret") ||
			fixtureBShort.equals("turret")) {
			
			contact.setEnabled(false);
			
		}
		
		// ignore all collisions when player in spawn phase
		
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		if (fixtureAShort.equals("spawn") ||
			fixtureBShort.equals("spawn")) {
			
			contact.setEnabled(false);
			
		}
	
	}
	
	public void postSolve(Contact contact, ContactImpulse contactimpulse) {
			
	}

}