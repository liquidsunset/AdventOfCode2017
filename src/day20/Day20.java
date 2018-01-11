package day20;

import Utils.Helper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.geometry.Point3D;

public class Day20 {

  private static List<Particle> particles = new ArrayList<>();

  public static void main(String[] args) {

    initParticles();
    for (int i = 0; i < 500; i++) {
      tick();
    }

    System.out
        .println("Part 1: " + particles.stream().min(Comparator.comparing(Particle::calcDistance))
            .get().id);
    initParticles();

    for (int i = 0; i < 500; i++) {
      tick();
      resolveCollisions();
    }

    System.out.println("Part 2: " + particles.size());

  }

  private static void initParticles() {
    particles.clear();
    try (Stream<String> fileStream = Files.lines(Paths.get("resources/day20.txt"))) {

      fileStream.forEach(line -> {
        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(line);

        Particle particle = new Particle();
        for (int i = 0; matcher.find(); i++) {
          List<Integer> list = Helper
              .getIntegerListFromString(matcher.group().replaceAll("[<>]", ""), ",");
          if (i == 0) {
            particle.position = new Point3D(list.get(0), list.get(1), list.get(2));
          } else if (i == 1) {
            particle.velocity = new Point3D(list.get(0), list.get(1), list.get(2));
          } else {
            particle.acceleration = new Point3D(list.get(0), list.get(1), list.get(2));
          }
        }
        particle.id = particles.size();
        particles.add(particle);
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void tick() {
    particles.forEach(Particle::applyTick);
  }

  private static void resolveCollisions() {
    particles = particles.stream()
        .collect(Collectors.groupingBy(Particle::getPosition))
        .values().stream()
        .filter(x -> x.size() == 1)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  static class Particle {

    Point3D position;
    Point3D velocity;
    Point3D acceleration;
    int id;

    int calcDistance() {
      return (int) (Math.abs(position.getX()) + Math.abs(position.getY()) + Math
          .abs(position.getZ()));
    }

    void applyTick() {
      velocity = velocity.add(acceleration);
      position = position.add(velocity);
    }

    Point3D getPosition() {
      return position;
    }
  }
}


