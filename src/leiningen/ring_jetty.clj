(ns leiningen.ring-jetty
  (:use [leiningen.help :only (help-for subtask-help-for)]
        [leiningen.ring-jetty.server :only (server)]
        [leiningen.ring-jetty.server-headless :only (server-headless)]
        [leiningen.ring-jetty.jar :only (jar)]
        [leiningen.ring-jetty.uberjar :only (uberjar)]
        [leiningen.ring-jetty.war :only (war)]
        [leiningen.ring-jetty.uberwar :only (uberwar)]))

(defn- nary? [v n]
  (some #{n} (map count (:arglists (meta v)))))

(defn ring-jetty
  "Manage a Ring-based application."
  {:help-arglists '([server server-headless war uberwar jar uberjar])
   :subtasks [#'server #'server-headless #'war #'uberwar #'jar #'uberjar]}
  ([project]
     (println (if (nary? #'help-for 2)
                (help-for project "ring-jetty")
                (help-for "ring-jetty"))))
  ([project subtask & args]
     (case subtask
       "server"          (apply server project args)
       "server-headless" (apply server-headless project args)
       "jar"             (apply jar project args)
       "uberjar"         (apply uberjar project args)
       "war"             (apply war project args)
       "uberwar"         (apply uberwar project args)
                         (println "Subtask" (str \" subtask \") "not found."
                                  (subtask-help-for *ns* #'ring-jetty)))))
