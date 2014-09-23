(ns leiningen.ring-jetty.server-headless
  (:use leiningen.ring-jetty.server))

(defn server-headless
  "Start a Ring server without opening a browser."
  ([project]
     (server-task project {:open-browser? false}))
  ([project port]
     (server-task project {:port (Integer. port), :open-browser? false})))
