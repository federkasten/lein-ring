(ns leiningen.ring-jetty.uberjar
  (:use [leiningen.ring-jetty.util :only (ensure-handler-set!)]
        [leiningen.ring-jetty.server :only (add-server-dep)])
  (:require [leiningen.ring-jetty.jar :as jar]
            leiningen.uberjar))

(defn uberjar
  "Create an executable $PROJECT-$VERSION.jar file with dependencies."
  [project]
  (ensure-handler-set! project)
  (let [project (-> project add-server-dep jar/add-main-class)]
    (jar/compile-main project)
    (leiningen.uberjar/uberjar project)))
