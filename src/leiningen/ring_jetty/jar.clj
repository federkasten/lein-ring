(ns leiningen.ring-jetty.jar
  (:use [leiningen.ring-jetty.util :only (compile-form ensure-handler-set! update-project)]
        [leiningen.ring-jetty.server :only (add-server-dep)])
  (:require [clojure.string :as str]
            leiningen.jar))

(defn default-main-namespace [project]
  (let [handler-sym (get-in project [:ring :handler])]
    (str (namespace handler-sym) ".main")))

(defn main-namespace [project]
  (or (get-in project [:ring :main])
      (default-main-namespace project)))

(defn compile-main [project]
  (let [main-ns (symbol (main-namespace project))
        options (-> (select-keys project [:ring])
                    (assoc-in [:ring :open-browser?] false)
                    (assoc-in [:ring :stacktraces?] false)
                    (assoc-in [:ring :auto-reload?] false))]
    (compile-form project main-ns
      `(do (ns ~main-ns
             (:require ring.server.leiningen)
             (:gen-class))
           (defn ~'-main []
             (ring.server.leiningen/serve '~options))))))

(defn add-main-class [project]
  (update-project project assoc :main (symbol (main-namespace project))))

(defn jar
  "Create an executable $PROJECT-$VERSION.jar file."
  [project]
  (ensure-handler-set! project)
  (let [project (-> project add-server-dep add-main-class)]
    (compile-main project)
    (leiningen.jar/jar project)))
