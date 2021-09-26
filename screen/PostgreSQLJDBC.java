package kunal.game.screen;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import kunal.game.SpaceGame;
import kunal.game.tools.ScrollingBackground;
public class PostgreSQLJDBC  extends GameOverScreen{
   public PostgreSQLJDBC(SpaceGame game, int score) throws Throwable {
		super(game, score);
		// TODO Auto-generated constructor stub
	}

public static void main(String args[]) throws Exception, ClassNotFoundException {
	   Connection con;
		PreparedStatement ps;
		
		int n,a;
		int score=SpaceGame.score;
		
		Class.forName("org.postgresql.Driver");
		con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/ass5","postgres","ajay25");
		String sql=("insert into game values("+score+")");
		ps=con.prepareStatement(sql);
		n=ps.executeUpdate();
		if(n>0)
			con.close();
		ps.close();
   }

@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void render(float delta) {
	// TODO Auto-generated method stub
	
}

@Override
public void resize(int width, int height) {
	// TODO Auto-generated method stub
	
}

@Override
public void pause() {
	// TODO Auto-generated method stub
	
}

@Override
public void resume() {
	// TODO Auto-generated method stub
	
}

@Override
public void hide() {
	// TODO Auto-generated method stub
	
}

@Override
public void dispose() {
	// TODO Auto-generated method stub
	
}
}