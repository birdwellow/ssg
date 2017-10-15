var gulp = require('gulp'),
    gutil = require('gulp-util'),
    sass = require('gulp-sass'),
    browserSync = require('browser-sync'),
    uglify = require('gulp-uglify'),
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat');


var packageJson = require('./package.json');

packageJson.gulpBuildIncludes.forEach(function (currentValue, index, array) {
  "use strict";
  array[index] = "node_modules/" + currentValue;
});

var gulpSrc = packageJson.gulpBuildIncludes.concat([
  'src/js/**/*.js',
  'src/scss/style.scss'
]);


/**
 * JS Build
 */

var gulpJsSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.js') !== -1;
});

gulp.task('js',function(){
  gulp.src(gulpJsSrc)
    // .pipe(jshint('.jshintrc'))
    // .pipe(jshint.reporter('default'))
    .pipe(concat('main.js'))
    .pipe(gulp.dest('dist/js'))
    .on('error', function (err) {
      gutil.log(gutil.colors.red('[Error]'),
        err.toString()
      );
    })
    .pipe(browserSync.reload({stream:true, once: true}));
});


/**
 * SASS Build
 */

var gulpSassSrc = gulpSrc.filter(function (currentValue) {
  return currentValue.indexOf('.scss') !== -1;
});

gulp.task('css', function () {
  return gulp.src(gulpSassSrc)
    .pipe(sass().on('error', sass.logError))
    .pipe(concat('main.css'))
    .pipe(gulp.dest('dist/css'))
    .pipe(browserSync.reload({stream:true}));
});


/**
 * HTML Build
 */

gulp.task('html',function(){
  gulp.src('src/**/*.html')
    .pipe(gulp.dest('dist/'))
    .pipe(browserSync.reload({stream:true, once: true}));
});


/**
 * Live reload
 */

gulp.task('browser-sync', function() {
    browserSync.init(null, {
        server: {
            baseDir: "dist"
        }
    });
});

gulp.task('bs-reload', function () {
    browserSync.reload();
});


/**
 * Main tasks
 */

gulp.task('watch', ['dev', 'browser-sync'], function () {
  gulp.watch("src/scss/**/*.scss", ['css']);
  gulp.watch("src/js/**/*.js", ['js']);
  gulp.watch("src/**/*.html", ['html']);
  gulp.watch("dist/*.html", ['bs-reload']);
});

gulp.task('dev', ['html', 'css', 'js']);

gulp.task('default', ['dev']);
