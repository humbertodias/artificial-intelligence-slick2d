package spaceinvaders.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class BFont extends UnicodeFont {

	@SuppressWarnings("unchecked")
	public BFont(String fontName, int size) throws SlickException {
		super(fontName, size, false, false);
		this.addAsciiGlyphs();
		this.addGlyphs(400, 600);
		this.getEffects().add(new ColorEffect());
		this.loadGlyphs();
	}
	
}
