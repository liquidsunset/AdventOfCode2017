package day24;


public class Day24Component {

  private final int portA;
  private final int portB;
  private final int strength;
  private boolean used;

  Day24Component(int portA, int portB) {
    this.portA = portA;
    this.portB = portB;
    strength = portA + portB;
  }

  public void setUsed() {
    used = true;
  }

  public void setUnused() {
    used = false;
  }

  public boolean getUsed() {
    return used;
  }

  public int getPortA() {
    return portA;
  }

  public int getPortB() {
    return portB;
  }

  public int getStrength() {
    return strength;
  }

  public int switchPort(int usedPort) {
    return usedPort == portA ? portB : portA;
  }
}
