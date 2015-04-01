(ns github-trending.core
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as string])
  (:gen-class))
  
(def url "https://github.com/trending")

(def popular-langs #{"Java" "JavaScript" "C" "C++" "C#" "PHP" "Python"
                     "Ruby" "CSS" "HTML" "Perl" "Objective-C" "Shell"})

(defn get-page 
  [url]
  (html/html-resource (java.net.URL. url)))

(defn get-url
  [html-selectable]
  (-> (html/select html-selectable [:h3.repo-list-name :a])
      (first)
      (get-in [:attrs :href])))
      
(defn get-desc
  [html-selectable]
  (-> (html/select html-selectable [:p.repo-list-description])
      (first)
      (html/text)
      (string/trim)))
      
(defn get-data
  [html-selectable]
  (-> (html/select html-selectable [:p.repo-list-meta])
      (first)
      (html/text)
      (string/split #"\u2022")
      (#(map string/trim %))))

(defn get-stars
  [stars-desc]
  (let [parts (string/split stars-desc #"\s+")]
    (if (= 3 (count parts))
      (->> (first parts)
           (remove #(= \, %))
           (apply str)
           (Integer/parseInt))
      "No language")))

(defn get-repo-data
  [html-selectable]
  (let [url (get-url html-selectable)
        desc (get-desc html-selectable)
        data (get-data html-selectable)]
        
        {:url url
         :desc desc
         :lang (first data)
         :stars (-> (second data)
                    (get-stars))}))

(defn remove-popular-langs
  [data]
  (remove #(popular-langs (:lang %)) data))

(defn -main
  [& args]
  (->> (get-page url)
       (#(html/select % [:li.repo-list-item]))
       (map get-repo-data)
       (remove-popular-langs)
       (map #(str (:url %) \newline
                  (:desc %) \newline
                  (:lang %) \newline
                  (:stars %) \newline))
        (string/join \newline)
        (println)))
