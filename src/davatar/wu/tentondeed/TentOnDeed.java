package davatar.wu.tentondeed;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gotti.wurmunlimited.modloader.classhooks.HookException;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import javassist.CtClass;
import javassist.CtMethod;

public class TentOnDeed implements WurmServerMod, PreInitable {
    private static Logger logger = Logger.getLogger("TentOnDeed");
    
    public static void logException(String msg, Throwable e) {
        if (logger != null) { logger.log(Level.SEVERE, msg, e); }
    }

    public static void logInfo(String msg) {
        if (logger != null) { logger.log(Level.INFO, msg); }
    }

    public String getVersion() {
    	return "0.1";
    }
    
	@Override
	public void preInit() {
		try {
			CtClass ctClass = HookManager.getInstance().getClassPool().getCtClass("com.wurmonline.server.behaviours.MethodsItems");
			CtMethod mayDropTentOnTile = ctClass.getDeclaredMethod("mayDropTentOnTile");
			logInfo("Allowing tents to be placed on the deed of the owning player.");
			mayDropTentOnTile.insertBefore("{ if(t != null && t.getVillage() != null && t.getVillage() == performer.getCitizenVillage()) { return true; } }");
		} catch(Exception e) {
			logException("preInit: ", e);
			e.printStackTrace();
			throw new HookException(e);
		}
	}
}
